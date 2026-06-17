package com.dnd.digimax.core.bootstrap;

import android.content.Context;

public class BootStateManager {
    public static final String STATE_PLAYER = "PLAYER";
    public static final String STATE_SETUP = "SETUP";
    public static final String STATE_RECOVERY = "RECOVERY";
    private final Context context;
    public BootStateManager(Context context) {
        this.context = context.getApplicationContext();
    }
    public String resolve() {

        if (isFirstRun()) {
            return STATE_SETUP;
        }

        if (isRecoveryNeeded()) {
            return STATE_RECOVERY;
        }

        return STATE_PLAYER;
    }

    // =============================
    // CONDITIONS
    // =============================

    private boolean isFirstRun() {
        return false; // nanti bisa pakai prefs / device binding
    }
    private boolean isRecoveryNeeded() {
        return false; // nanti integrate watchdog / crash flag
    }
}