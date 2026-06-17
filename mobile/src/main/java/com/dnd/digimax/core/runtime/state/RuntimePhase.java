package com.dnd.digimax.core.runtime.state;

public enum RuntimePhase {

    NONE,

    INITIALIZING,

    READY,

    RUNNING,

    RECOVERY,

    STOPPED,

    BOOTSTRAP,

    INSPECT,

    SECURITY,

    CLUSTER_SYNC,

    CONTENT_LOAD,

    COMPLETE
}
