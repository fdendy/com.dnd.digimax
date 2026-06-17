package com.dnd.digimax.core.system.watchdog;

import android.os.Handler;
import android.os.Looper;

import com.dnd.digimax.core.state.StateBus;
import com.dnd.digimax.core.state.StateEvent;
import com.dnd.digimax.core.state.StateListener;
import com.dnd.digimax.shared.constant.RuntimeEvents;

public class PlaybackHealthChecker
        implements StateListener {

    //================================================
    // CONFIG
    //================================================

    private static final long CHECK_INTERVAL =
            10000;

    private static final long PLAYBACK_TIMEOUT =
            60000;

    //================================================
    // STATE
    //================================================

    private long lastPlaybackTimestamp =
            System.currentTimeMillis();

    private boolean running;

    //================================================
    // HANDLER
    //================================================

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    //================================================
    // START
    //================================================

    public void start() {

        if (running)
            return;

        running = true;

        StateBus.get().register(this);

        scheduleCheck();
    }

    //================================================
    // STOP
    //================================================

    public void stop() {

        running = false;

        StateBus.get().unregister(this);

        handler.removeCallbacksAndMessages(
                null
        );
    }

    //================================================
    // STATE EVENT
    //================================================

    @Override
    public void onStateChanged(
            StateEvent event
    ) {

        if (event == null)
            return;

        if (RuntimeEvents.PLAY.equals(
                event.type
        )) {

            lastPlaybackTimestamp =
                    System.currentTimeMillis();
        }
    }

    //================================================
    // CHECK LOOP
    //================================================

    private void scheduleCheck() {

        handler.postDelayed(() -> {

            if (!running)
                return;

            checkPlaybackHealth();

            scheduleCheck();

        }, CHECK_INTERVAL);
    }

    //================================================
    // CHECK PLAYBACK
    //================================================

    private void checkPlaybackHealth() {

        long now =
                System.currentTimeMillis();

        long elapsed =
                now - lastPlaybackTimestamp;

        if (elapsed > PLAYBACK_TIMEOUT) {

            notifyPlaybackTimeout();
        }
    }

    //================================================
    // TIMEOUT
    //================================================

    private void notifyPlaybackTimeout() {

        StateEvent event =
                new StateEvent();

        event.type =
                RuntimeEvents.ERROR;

        event.message =
                "PLAYBACK_TIMEOUT";

        StateBus.get().emit(event);
    }
}