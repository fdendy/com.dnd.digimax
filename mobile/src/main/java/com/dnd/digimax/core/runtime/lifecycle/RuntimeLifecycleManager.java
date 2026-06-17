package com.dnd.digimax.core.runtime.lifecycle;

import com.dnd.digimax.core.state.GlobalStateBus;

public final class RuntimeLifecycleManager {
    private static RuntimeState currentState = RuntimeState.BOOTING;
    private static long lastTransitionTimestamp = System.currentTimeMillis();
    private RuntimeLifecycleManager() {
    }
    public static synchronized RuntimeState getCurrentState() {
        return currentState;
    }

    public static synchronized boolean moveTo(RuntimeState nextState) {
        if (!RuntimeTransitionValidator.canTransition(currentState,nextState)) {
            return false;
        }
        RuntimeState previous = currentState;
        currentState = nextState;
        lastTransitionTimestamp = System.currentTimeMillis();
        publishState(previous, nextState);
        return true;
    }

    private static void publishState(RuntimeState previous,RuntimeState current) {
        GlobalStateBus.getInstance().publish("runtime_state",current);
        GlobalStateBus.getInstance().publish("runtime_previous_state",previous);
    }

    public static long getLastTransitionTimestamp() {
        return lastTransitionTimestamp;
    }

    public static boolean isRunning() {
        return currentState == RuntimeState.RUNNING;
    }

    public static boolean isRecoveryMode() {
        return currentState == RuntimeState.RECOVERY;
    }

    public static boolean isSafeMode() {
        return currentState == RuntimeState.SAFE_MODE;
    }
}