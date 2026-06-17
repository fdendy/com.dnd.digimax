package com.dnd.digimax.feature.playback.pop.sync;

import com.dnd.digimax.feature.playback.pop.storage.PopSyncManager;

public class PopSyncWorker {

    private final PopSyncManager syncManager;

    public PopSyncWorker(
            PopSyncManager syncManager) {

        this.syncManager =
                syncManager;
    }

    public boolean execute() {

        try {

            syncManager.loadPending();

            syncManager.sync();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}