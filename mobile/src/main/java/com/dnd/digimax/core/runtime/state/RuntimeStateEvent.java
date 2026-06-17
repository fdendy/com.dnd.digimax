package com.dnd.digimax.core.runtime.state;

public class RuntimeStateEvent {

    private final RuntimeState previousState;
    private final RuntimeState currentState;

    public RuntimeStateEvent(
            RuntimeState previousState,
            RuntimeState currentState) {

        this.previousState = previousState;
        this.currentState = currentState;
    }

    public RuntimeState getPreviousState() {
        return previousState;
    }

    public RuntimeState getCurrentState() {
        return currentState;
    }
}