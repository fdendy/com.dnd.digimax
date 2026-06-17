package com.dnd.digimax.core.runtime.inspector.recovery;

public enum RecoveryAction {

    NONE,

    // ringan
    CLEAN_CACHE,
    REDUCE_LOAD,

    // menengah
    RESTART_SERVICE,
    // berat
    RESTART_APP,
    ENTER_SAFE_MODE
}