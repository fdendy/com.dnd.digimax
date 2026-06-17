package com.dnd.digimax.core.cluster.network;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SyncManager {

    // =========================
    // STATE STORE
    // =========================

    private final Map<String, Object> localState = new ConcurrentHashMap<>();
    private final Map<String, Long> stateVersion = new ConcurrentHashMap<>();

    private volatile boolean isMaster = false;

    // =========================
    // ROLE SETTER
    // =========================

    public void setMaster(boolean master) {
        this.isMaster = master;
    }

    // =========================
    // STATE UPDATE (LOCAL WRITE)
    // =========================

    public synchronized void updateLocalState(String key, Object value) {

        localState.put(key, value);

        long version = System.currentTimeMillis();
        stateVersion.put(key, version);

        Log.d("SyncManager", "LOCAL UPDATE: " + key + " v" + version);

        if (isMaster) {
            broadcastState(key, value, version);
        }
    }

    // =========================
    // RECEIVE FROM CLUSTER
    // =========================

    public synchronized void onRemoteUpdate(String nodeId,
                                            String key,
                                            Object value,
                                            long version) {

        Long localVersion = stateVersion.get(key);

        // conflict resolution: latest wins
        if (localVersion == null || version > localVersion) {

            localState.put(key, value);
            stateVersion.put(key, version);

            Log.i("SyncManager",
                    "STATE SYNCED FROM " + nodeId + " KEY=" + key);
        } else {
            Log.w("SyncManager",
                    "REJECT OLD STATE FROM " + nodeId + " KEY=" + key);
        }
    }

    // =========================
    // MASTER BROADCAST
    // =========================

    private void broadcastState(String key, Object value, long version) {

        // NOTE: actual UDP send handled by UdpClient later
        Log.d("SyncManager",
                "BROADCAST STATE: " + key + " v" + version);
    }

    // =========================
    // FULL SYNC REQUEST (WORKER → MASTER)
    // =========================

    public Map<String, Object> requestFullState() {

        Log.i("SyncManager", "FULL STATE REQUESTED");

        return new HashMap<>(localState);
    }

    // =========================
    // STATE ACCESS
    // =========================

    public Object getState(String key) {
        return localState.get(key);
    }

    public Map<String, Object> getAllState() {
        return localState;
    }

    public Map<String, Long> getStateVersion() {
        return stateVersion;
    }
}