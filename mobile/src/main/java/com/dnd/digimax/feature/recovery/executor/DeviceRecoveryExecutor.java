package com.dnd.digimax.feature.recovery.executor;

import android.util.Log;

import com.dnd.digimax.feature.recovery.action.RecoveryAction;

public class DeviceRecoveryExecutor {

    private static final String TAG =
            "DeviceRecovery";

    public boolean execute(
            RecoveryAction action) {

        if (action ==
                RecoveryAction.REBOOT_DEVICE) {

            Log.w(
                    TAG,
                    "Executing REBOOT_DEVICE"
            );

            return true;
        }

        return false;
    }
}