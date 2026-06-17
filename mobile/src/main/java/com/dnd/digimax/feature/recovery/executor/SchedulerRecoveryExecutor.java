package com.dnd.digimax.feature.recovery.executor;

import android.util.Log;

import com.dnd.digimax.feature.recovery.action.RecoveryAction;

public class SchedulerRecoveryExecutor {

    private static final String TAG =
            "SchedulerRecovery";

    public boolean execute(
            RecoveryAction action) {

        switch (action) {

            case RELOAD_SCHEDULE:

            case REFRESH_CONTENT:

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