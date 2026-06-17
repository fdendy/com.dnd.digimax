package com.dnd.digimax.feature.recovery.executor;

import android.util.Log;

import com.dnd.digimax.feature.recovery.action.RecoveryAction;

public class PlaybackRecoveryExecutor {

    private static final String TAG =
            "PlaybackRecovery";

    public boolean execute(
            RecoveryAction action) {

        switch (action) {

            case RETRY:

            case RELOAD_PLAYBACK:

            case RESTART_PLUGIN:

                Log.w(
                        TAG,
                        "Executing " + action
                );

                return true;

            default:

                return false;
        }
    }
}