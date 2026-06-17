package com.dnd.digimax.feature.monitoring.policy;

public class MonitoringPolicy {

    private int maxPlaybackErrors = 10;

    private int maxPendingPop = 1000;

    private int maxTelemetryQueue = 5000;

    private int maxRuntimeRisk = 75;

    public int getMaxPlaybackErrors() {
        return maxPlaybackErrors;
    }

    public int getMaxPendingPop() {
        return maxPendingPop;
    }

    public int getMaxTelemetryQueue() {
        return maxTelemetryQueue;
    }

    public int getMaxRuntimeRisk() {
        return maxRuntimeRisk;
    }
}