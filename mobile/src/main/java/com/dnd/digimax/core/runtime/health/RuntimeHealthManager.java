package com.dnd.digimax.core.runtime.health;

import com.dnd.digimax.core.state.GlobalStateBus;

public class RuntimeHealthManager {
    private final RuntimeRiskAnalyzer analyzer;
    private int lastRiskScore = 0;
    private int consecutiveDegradedCount = 0;
    private int consecutiveCriticalCount = 0;
    private long lastStableTimestamp = System.currentTimeMillis();
    private long lastStateChangeTimestamp = System.currentTimeMillis();
    private RuntimeHealthState currentState = RuntimeHealthState.STABLE;
    public RuntimeHealthManager(RuntimeRiskAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
    public synchronized void update() {
        int risk = analyzer.calculateRiskScore();
        RuntimeHealthState previous = currentState;
        evaluateTrend(risk);
        if (previous != currentState) {
            lastStateChangeTimestamp = System.currentTimeMillis();
            publishState(previous, currentState);
            onStateChanged(previous, currentState);
        }
        lastRiskScore = risk;
    }
    private void evaluateTrend(int risk) {

        if (risk == 0) {

            handleStable();

        } else if (risk <= 2) {

            handleDegraded();

        } else {

            handleCritical();
        }
    }
    private void handleStable() {
        consecutiveDegradedCount = 0;
        consecutiveCriticalCount = 0;
        currentState = RuntimeHealthState.STABLE;
        lastStableTimestamp = System.currentTimeMillis();
    }
    private void handleDegraded() {

        consecutiveDegradedCount++;

        consecutiveCriticalCount = 0;

        if (consecutiveDegradedCount >= 3) {

            currentState =
                    RuntimeHealthState.UNSTABLE;

        } else {

            currentState =
                    RuntimeHealthState.DEGRADED;
        }
    }
    private void handleCritical() {
        consecutiveCriticalCount++;
        consecutiveDegradedCount = 0;
        if (consecutiveCriticalCount >= 2) {
            currentState = RuntimeHealthState.CRITICAL;
        } else {
            currentState = RuntimeHealthState.DEGRADED;
        }
    }
    private void publishState(RuntimeHealthState previous, RuntimeHealthState current) {
        GlobalStateBus.getInstance().publish("runtime_health", current);
        GlobalStateBus.getInstance().publish("runtime_health_previous",previous);
        GlobalStateBus.getInstance().publish("runtime_risk_score",lastRiskScore);
    }
    private void onStateChanged(RuntimeHealthState from, RuntimeHealthState to) {
        switch (to) {
            case UNSTABLE:
                markSystemUnstable();
                break;
            case CRITICAL:
                markSystemCritical();
                break;
        }
    }
    public RuntimeHealthState getCurrentState() {
        return currentState;
    }
    public boolean isStable() {
        return currentState == RuntimeHealthState.STABLE;
    }
    public boolean isUnstable() {
        return currentState == RuntimeHealthState.UNSTABLE;
    }
    public boolean isCritical() {
        return currentState == RuntimeHealthState.CRITICAL;
    }
    public boolean shouldTriggerSoftRecovery() {
        return currentState == RuntimeHealthState.UNSTABLE;
    }
    public boolean shouldTriggerHardRecovery() {
        return currentState == RuntimeHealthState.CRITICAL;
    }
    public long getStableDuration() {
        return System.currentTimeMillis() - lastStableTimestamp;
    }
    public long getLastStateChangeTimestamp() {
        return lastStateChangeTimestamp;
    }
    public int getLastRiskScore() {
        return lastRiskScore;
    }
    private void markSystemUnstable() {
        GlobalStateBus.getInstance().publish("runtime_recovery_required",true);
    }
    private void markSystemCritical() {
        GlobalStateBus.getInstance().publish("runtime_hard_recovery_required", true);
    }
}