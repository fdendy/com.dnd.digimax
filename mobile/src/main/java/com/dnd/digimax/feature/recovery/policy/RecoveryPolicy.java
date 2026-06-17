package com.dnd.digimax.feature.recovery.policy;

public class RecoveryPolicy {

    private int maxRetryCount = 3;

    private boolean allowRestartPlayer = true;

    private boolean allowRebootDevice = true;

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public boolean isAllowRestartPlayer() {
        return allowRestartPlayer;
    }

    public boolean isAllowRebootDevice() {
        return allowRebootDevice;
    }
}