package com.dnd.digimax.core.runtime.lifecycle;

public enum RuntimeState {
    BOOTING,
    INITIALIZING,
    SECURITY_CHECK,
    RUNTIME_READY,
    PLAYER_READY,
    RUNNING,
    RECOVERY,
    SAFE_MODE,
    SHUTDOWN
}