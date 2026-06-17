package com.dnd.digimax.core.cluster.node;

import android.util.Log;

import com.dnd.digimax.core.cluster.network.UdpClient;
import com.dnd.digimax.core.cluster.role.RoleManager;

/**
 * WorkerNode = passive execution node in cluster.
 *
 * Responsibilities:
 * - report heartbeat
 * - request sync from master
 * - listen to master commands
 * - stay ready for role change (election failover)
 */
public class WorkerNode extends BaseNode {

    private static final String TAG = "WorkerNode";

    private static final int HEARTBEAT_PORT = 9000;
    private static final int SYNC_PORT = 9001;

    private volatile boolean running = false;

    public WorkerNode(String nodeId,
                      UdpClient udpClient,
                      RoleManager roleManager) {
        super(nodeId, udpClient, roleManager);
    }

    // =========================
    // LIFECYCLE
    // =========================

    @Override
    protected void onStart() {
        running = true;

        Log.i(TAG, "WORKER STARTED");

        startHeartbeatReport();
        startSyncRequester();
    }

    @Override
    protected void onStop() {
        running = false;

        Log.i(TAG, "WORKER STOPPED");
    }

    // =========================
    // WORKER BEHAVIOR
    // =========================

    /**
     * Worker sends heartbeat TO cluster (not broadcast authority).
     */
    private void startHeartbeatReport() {

        new Thread(() -> {

            while (running) {
                try {

                    String payload = "WORKER_ALIVE|" + nodeId;

                    // send to master broadcast channel (or known master IP in real system)
                    broadcast(payload, HEARTBEAT_PORT);

                    Thread.sleep(3000);

                } catch (Exception e) {
                    Log.e(TAG, "Heartbeat error", e);
                }
            }

        }, "worker-heartbeat").start();
    }

    /**
     * Worker actively requests sync from master.
     */
    private void startSyncRequester() {

        new Thread(() -> {

            while (running) {
                try {

                    String request = "WORKER_REQUEST_SYNC|" + nodeId;

                    broadcast(request, SYNC_PORT);

                    Thread.sleep(7000);

                } catch (Exception e) {
                    Log.e(TAG, "Sync request error", e);
                }
            }

        }, "worker-sync").start();
    }

    // =========================
    // MESSAGE HANDLER
    // =========================

    @Override
    protected void onMessage(String from, String message) {

        Log.d(TAG, "Message from " + from + ": " + message);

        if (message == null) return;

        if (message.contains("SYNC_RESPONSE")) {
            handleSyncResponse(message);
        }

        if (message.contains("MASTER_ALIVE")) {
            handleMasterHeartbeat(from);
        }

        if (message.contains("ELECTION_RESULT")) {
            handleElectionResult(message);
        }
    }

    // =========================
    // HANDLERS
    // =========================

    private void handleSyncResponse(String message) {
        Log.d(TAG, "Sync received: " + message);
        // future: apply state diff to RuntimeStateManager
    }

    private void handleMasterHeartbeat(String from) {
        Log.d(TAG, "Master alive: " + from);
        // future: reset failover timer
    }

    private void handleElectionResult(String message) {

        Log.w(TAG, "Election update: " + message);

        if (message.contains("MASTER_CONFIRMED")) {
            Log.i(TAG, "Still worker under active master");
        }

        if (message.contains("MASTER_LOST")) {
            Log.e(TAG, "Master lost → trigger election recovery");
            // future hook: MasterElectionService.triggerElection()
        }
    }

    // =========================
    // ROLE CHECK
    // =========================

    public boolean isWorkerNode() {
        return isWorker();
    }
}