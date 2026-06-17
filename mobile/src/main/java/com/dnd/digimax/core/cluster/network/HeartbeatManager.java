package com.dnd.digimax.core.cluster.network;

import android.util.Log;

import com.dnd.digimax.core.cluster.election.MasterElectionService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatManager {

    // =========================
    // CONFIG
    // =========================

    private static final long HEARTBEAT_INTERVAL_MS = 2000;
    private static final long TIMEOUT_MS = 5000;

    // =========================
    // STATE
    // =========================

    private final Map<String, Long> nodeLastSeen = new HashMap<>();

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private final MasterElectionService electionService;

    private final String localNodeId;

    // =========================
    // INIT
    // =========================

    public HeartbeatManager(String localNodeId,
                            MasterElectionService electionService) {

        this.localNodeId = localNodeId;
        this.electionService = electionService;
    }

    // =========================
    // START LOOP
    // =========================

    public void start() {

        scheduler.scheduleAtFixedRate(
                this::tick,
                0,
                HEARTBEAT_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );

        Log.i("Heartbeat", "STARTED");
    }

    // =========================
    // HEARTBEAT RECEIVE
    // =========================

    public synchronized void onHeartbeat(String nodeId) {

        nodeLastSeen.put(nodeId, System.currentTimeMillis());

        Log.d("Heartbeat", "RECEIVED: " + nodeId);
    }

    // =========================
    // HEARTBEAT TICK ENGINE
    // =========================

    private void tick() {

        long now = System.currentTimeMillis();

        boolean hasDeadNodes = false;

        for (Map.Entry<String, Long> entry : nodeLastSeen.entrySet()) {

            long lastSeen = entry.getValue();

            if (now - lastSeen > TIMEOUT_MS) {

                Log.w("Heartbeat", "NODE DEAD: " + entry.getKey());

                nodeLastSeen.remove(entry.getKey());
                hasDeadNodes = true;
            }
        }

        // trigger re-election if cluster changed
        if (hasDeadNodes) {
            triggerReElection();
        }
    }

    // =========================
    // RE-ELECTION TRIGGER
    // =========================

    private void triggerReElection() {

        String[] activeNodes = nodeLastSeen.keySet()
                .toArray(new String[0]);

        electionService.runElection(activeNodes);
    }

    // =========================
    // HEARTBEAT SEND (LOCAL SIGNAL)
    // =========================

    public void sendHeartbeat() {

        nodeLastSeen.put(localNodeId, System.currentTimeMillis());

        Log.d("Heartbeat", "SEND: " + localNodeId);
    }

    // =========================
    // DEBUG STATE
    // =========================

    public Map<String, Long> getNodeLastSeen() {
        return nodeLastSeen;
    }
}