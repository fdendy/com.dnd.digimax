package com.dnd.digimax.core.runtime.state;

public enum RuntimePhase {

    NONE,

    BOOTSTRAP,

    INSPECT,

    SECURITY,

    CLUSTER_SYNC,

    CONTENT_LOAD,

    COMPLETE
}