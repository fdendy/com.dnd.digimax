package com.dnd.digimax.core.runtime.state;

public class RuntimeStateMachine {

    private final RuntimeContext context;
    private final RuntimeStateRegistry registry;
    private final RuntimeTransitionManager transitionManager;

    public RuntimeStateMachine() {

        context = new RuntimeContext();

        registry =
                new RuntimeStateRegistry();

        transitionManager =
                new RuntimeTransitionManager();
    }

    public RuntimeState getState() {
        return context.getState();
    }

    public RuntimePhase getPhase() {
        return context.getPhase();
    }

    public void updatePhase(
            RuntimePhase phase) {

        context.setPhase(phase);
    }

    public void moveTo(
            RuntimePhase phase) {

        updatePhase(phase);
    }

    public synchronized boolean transition(
            RuntimeState targetState) {

        RuntimeState currentState =
                context.getState();

        if (!transitionManager.canTransition(
                currentState,
                targetState)) {

            return false;
        }

        context.setState(targetState);

        RuntimeStateEvent event =
                new RuntimeStateEvent(
                        currentState,
                        targetState);

        registry.notifyObservers(event);

        return true;
    }

    public RuntimeStateSnapshot snapshot() {

        return new RuntimeStateSnapshot(
                context.getState(),
                context.getPhase());
    }

    public void registerObserver(
            RuntimeStateObserver observer) {

        registry.register(observer);
    }

    public void unregisterObserver(
            RuntimeStateObserver observer) {

        registry.unregister(observer);
    }
}
