package com.dnd.digimax.core.runtime.state;

public class RuntimeContext {

    private RuntimeState state;
    private RuntimePhase phase;

    public RuntimeContext() {
        state = RuntimeState.OFFLINE;
        phase = RuntimePhase.NONE;
    }

    public RuntimeState getState() {
        return state;
    }

    public void setState(RuntimeState state) {
        this.state = state;
    }

    public RuntimePhase getPhase() {
        return phase;
    }

    public void setPhase(RuntimePhase phase) {
        this.phase = phase;
    }
}