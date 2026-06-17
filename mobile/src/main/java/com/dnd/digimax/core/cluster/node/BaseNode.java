package com.dnd.digimax.core.cluster.node;

import android.util.Log;

import com.dnd.digimax.core.cluster.network.UdpClient;
import com.dnd.digimax.core.cluster.role.RoleManager;

/**
 * BaseNode = fundamental unit in cluster system.
 *
 * Semua node (Master / Worker) wajib extend ini.
 *
 * Responsibilities:
 * - lifecycle node
 * - identity
 * - basic communication layer
 * - role awareness
 */
public abstract class BaseNode {

    private static final String TAG = "BaseNode";

    protected final UdpClient udpClient;
    protected final RoleManager roleManager;

    protected final String nodeId;
    protected volatile boolean active = false;

    public BaseNode(String nodeId,
                    UdpClient udpClient,
                    RoleManager roleManager) {

        this.nodeId = nodeId;
        this.udpClient = udpClient;
        this.roleManager = roleManager;
    }

    // =========================
    // LIFECYCLE
    // =========================

    public void start() {
        if (active) return;

        active = true;
        onStart();

        Log.i(TAG, nodeId + " started");
    }

    public void stop() {
        if (!active) return;

        active = false;
        onStop();

        Log.i(TAG, nodeId + " stopped");
    }

    public boolean isActive() {
        return active;
    }

    // =========================
    // ROLE SYSTEM
    // =========================

    public boolean isMaster() {
        return roleManager != null && roleManager.isMaster();
    }

    public boolean isWorker() {
        return roleManager != null && roleManager.isWorker();
    }

    // =========================
    // COMMUNICATION
    // =========================

    protected void send(String message, String host, int port) {
        if (!active) return;

        udpClient.send(
                wrapMessage(message),
                host,
                port
        );
    }

    protected void broadcast(String message, int port) {
        if (!active) return;

        udpClient.send(
                wrapMessage(message),
                "255.255.255.255",
                port
        );
    }

    // =========================
    // MESSAGE ENVELOPE
    // =========================

    private String wrapMessage(String payload) {
        return nodeId + "|" + System.currentTimeMillis() + "|" + payload;
    }

    // =========================
    // HOOKS (override)
    // =========================

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void onMessage(String from, String message);

}