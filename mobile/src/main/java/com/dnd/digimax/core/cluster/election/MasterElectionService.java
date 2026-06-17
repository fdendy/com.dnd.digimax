package com.dnd.digimax.core.cluster.election;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class MasterElectionService {

    // =========================
    // NODE STATE
    // =========================

    private volatile String localNodeId;
    private volatile String currentMasterId;

    private final AtomicBoolean isMaster = new AtomicBoolean(false);

    // =========================
    // INIT
    // =========================

    public MasterElectionService(String localNodeId) {
        this.localNodeId = localNodeId;
    }

    // =========================
    // ELECTION ENTRY POINT
    // =========================

    public synchronized void runElection(String[] activeNodes) {

        if (activeNodes == null || activeNodes.length == 0) {
            becomeMaster();
            return;
        }

        String elected = electLowestNode(activeNodes);

        if (localNodeId.equals(elected)) {
            becomeMaster();
        } else {
            becomeWorker(elected);
        }
    }

    // =========================
    // CORE ELECTION RULE
    // =========================

    private String electLowestNode(String[] nodes) {

        String lowest = localNodeId;

        for (String node : nodes) {

            if (node == null) continue;

            if (node.compareTo(lowest) < 0) {
                lowest = node;
            }
        }

        return lowest;
    }

    // =========================
    // ROLE TRANSITION
    // =========================

    private void becomeMaster() {

        if (isMaster.get()) return;

        isMaster.set(true);
        currentMasterId = localNodeId;

        Log.i("Election", "BECOME MASTER: " + localNodeId);
    }

    private void becomeWorker(String masterId) {

        isMaster.set(false);
        currentMasterId = masterId;

        Log.i("Election", "BECOME WORKER | MASTER = " + masterId);
    }

    // =========================
    // HEARTBEAT FAILOVER HOOK
    // =========================

    public void onMasterLost() {

        Log.w("Election", "MASTER LOST → RE-ELECTION");

        isMaster.set(false);
    }

    // =========================
    // STATE ACCESS
    // =========================

    public boolean isMaster() {
        return isMaster.get();
    }

    public String getCurrentMasterId() {
        return currentMasterId;
    }

    public String getLocalNodeId() {
        return localNodeId;
    }

    public void updateLocalNodeId(String nodeId) {
        this.localNodeId = nodeId;
    }
}