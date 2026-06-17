package com.dnd.digimax.core.runtime.lifecycle;

public final class RuntimeTransitionValidator {

    private RuntimeTransitionValidator() {
    }

    public static boolean canTransition(RuntimeState current,RuntimeState next) {
        switch (current) {
            case BOOTING:
                return next == RuntimeState.INITIALIZING;
            case INITIALIZING:
                return next == RuntimeState.SECURITY_CHECK;
            case SECURITY_CHECK:
                return next == RuntimeState.RUNTIME_READY || next == RuntimeState.SAFE_MODE;
            case RUNTIME_READY:
                return next == RuntimeState.PLAYER_READY || next == RuntimeState.RECOVERY;
            case PLAYER_READY:
                return next == RuntimeState.RUNNING;
            case RUNNING:
                return next == RuntimeState.RECOVERY || next == RuntimeState.SHUTDOWN;
            case RECOVERY:
                return next == RuntimeState.RUNTIME_READY
                        || next == RuntimeState.SAFE_MODE
                        || next == RuntimeState.SHUTDOWN;
            case SAFE_MODE:
                return next == RuntimeState.RECOVERY
                        || next == RuntimeState.SHUTDOWN;
            default:
                return false;
        }
    }
}