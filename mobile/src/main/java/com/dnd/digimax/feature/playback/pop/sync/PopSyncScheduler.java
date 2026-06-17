package com.dnd.digimax.feature.playback.pop.sync;

import android.os.Handler;
import android.os.Looper;

public class PopSyncScheduler {

    private final Handler handler =
            new Handler(
                    Looper.getMainLooper()
            );

    private Runnable task;

    private long intervalMillis =
            60000;

    public void start(
            Runnable runnable) {

        stop();

        task =
                new Runnable() {

                    @Override
                    public void run() {

                        runnable.run();

                        handler.postDelayed(
                                this,
                                intervalMillis
                        );
                    }
                };

        handler.post(task);
    }

    public void stop() {

        if (task != null) {

            handler.removeCallbacks(
                    task
            );
        }
    }

    public void setIntervalMillis(
            long intervalMillis) {

        this.intervalMillis =
                intervalMillis;
    }
}