package com.dnd.digimax.core.bootstrap;

import android.content.Context;
import android.content.SharedPreferences;

public class BootPolicy {

    private static final String PREF = "digimax_boot";
    private static final String KEY_AUTO_START = "auto_start";

    private final Context context;

    public BootPolicy(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean shouldStartOnBoot() {

        // 🔥 default: true (signage behavior)
        return getPrefs().getBoolean(KEY_AUTO_START, true);
    }

    public void setAutoStart(boolean enable) {
        getPrefs().edit().putBoolean(KEY_AUTO_START, enable).apply();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }
}