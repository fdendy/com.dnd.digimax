package com.dnd.digimax.core.cluster.node;

import android.util.Log;

import com.dnd.digimax.core.cluster.network.UdpClient;
import com.dnd.digimax.core.cluster.role.RoleManager;

/**
 * MasterNode = authoritative node in cluster.
 *
 * Responsibilities:
 * - Broadcast cluster state
 * - Coordinate sync
 * - Respond to workers
 * - Maintain leadership heartbeat
 */
public class MasterNode extends BaseNode {

    private static final String TAG = "MasterNode";

    private static final int HEARTBEAT_PORT = 9000;
    private static final int SYNC_PORT = 9001;

    private volatile boolean running = false;

    public MasterNode(String nodeId,
                      UdpClient udpClient,
                      RoleManager roleManager) {
        super(nodeId, udpClient, roleManager);
    }

    // =========================
    // MASTER LIFECYCLE
    // =========================

    @Override
    protected void onStart() {
        running = true;

        Log.i(TAG, "MASTER STARTED");

        startHeartbeat();
        startSyncBroadcast();
    }

    @Override
    protected void onStop() {
        running = false;

        Log.i(TAG, "MASTER STOPPED");
    }

    // =========================
    // CORE MASTER BEHAVIOR
    // =========================

    private void startHeartbeat() {

        new Thread(() -> {

            while (running) {
                try {

                    String payload = "MASTER_ALIVE";

                    broadcast(payload, HEARTBEAT_PORT);

                    Thread.sleep(2000);

                } catch (Exception e) {
                    Log.e(TAG, "Heartbeat error", e);
                }
            }

        }, "master-heartbeat").start();
    }

    private void startSyncBroadcast() {

        new Thread(() -> {

            while (running) {
                try {

                    String syncPayload = buildSyncPayload();

                    broadcast(syncPayload, SYNC_PORT);

                    Thread.sleep(5000);

                } catch (Exception e) {
                    Log.e(TAG, "Sync broadcast error", e);
                }
            }

        }, "master-sync").start();
    }

    // =========================
    // SYNC PAYLOAD
    // =========================

    private String buildSyncPayload() {
        return "SYNC|" + System.currentTimeMillis() + "|STATE_OK";
    }

    // =========================
    // MESSAGE HANDLER
    // =========================

    @Override
    protected void onMessage(String from, String message) {

        Log.d(TAG, "Message from " + from + ": " + message);

        if (message == null) return;

        if (message.contains("WORKER_REQUEST_SYNC")) {
            handleSyncRequest(from);
        }

        if (message.contains("ELECTION_REQUEST")) {
            handleElectionRequest(from);
        }
    }

    // =========================
    // HANDLERS
    // =========================

    private void handleSyncRequest(String from) {
        send("SYNC_RESPONSE|OK", from, SYNC_PORT);
    }

    private void handleElectionRequest(String from) {
        send("ELECTION_RESULT|MASTER_CONFIRMED", from, HEARTBEAT_PORT);
    }

    // =========================
    // MASTER AUTHORITY
    // =========================

    public boolean isLeader() {
        return isMaster();
    }
}