package com.dnd.digimax.core.runtime.state;

public class RuntimeStateSnapshot {

    private final RuntimeState state;
    private final RuntimePhase phase;
    private final long timestamp;

    public RuntimeStateSnapshot(
            RuntimeState state,
            RuntimePhase phase) {

        this.state = state;
        this.phase = phase;
        this.timestamp = System.currentTimeMillis();
    }

    public RuntimeState getState() {
        return state;
    }

    public RuntimePhase getPhase() {
        return phase;
    }

    public long getTimestamp() {
        return timestamp;
    }
}