package com.dnd.digimax.feature.recovery.executor;

import android.util.Log;

import com.dnd.digimax.feature.recovery.action.RecoveryAction;

public class RuntimeRecoveryExecutor {

    private static final String TAG =
            "RuntimeRecovery";

    public boolean execute(
            RecoveryAction action) {

        switch (action) {

            case CLEAR_CACHE:

            case RESTART_PLAYER:

            case RESTART_RUNTIME:

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