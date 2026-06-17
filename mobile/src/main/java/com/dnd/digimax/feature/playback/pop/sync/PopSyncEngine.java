package com.dnd.digimax.feature.playback.pop.sync;

import com.dnd.digimax.feature.playback.pop.storage.PopSyncManager;

public class PopSyncEngine {

    private final PopSyncWorker worker;

    private final PopSyncScheduler scheduler;

    private volatile PopSyncState state =
            PopSyncState.IDLE;

    public PopSyncEngine(
            PopSyncManager syncManager) {

        worker =
                new PopSyncWorker(
                        syncManager
                );

        scheduler =
                new PopSyncScheduler();
    }

    public void start() {

        state =
                PopSyncState.SCHEDULED;

        scheduler.start(
                this::syncNow
        );
    }

    public void stop() {

        scheduler.stop();

        state =
                PopSyncState.IDLE;
    }

    public synchronized void syncNow() {

        state =
                PopSyncState.RUNNING;

        boolean success =
                worker.execute();

        state =
                success
                        ? PopSyncState.SUCCESS
                        : PopSyncState.FAILED;
    }

    public PopSyncState getState() {

        return state;
    }
}