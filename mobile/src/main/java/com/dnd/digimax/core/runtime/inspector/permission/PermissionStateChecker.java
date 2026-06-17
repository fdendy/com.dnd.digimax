package com.dnd.digimax.core.runtime.inspector.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

public class PermissionStateChecker {
    private String strTAG = "PermissionStateChecker";
    private LogsSystemWriter logs = new LogsSystemWriter();
    private final Context context;

    public PermissionStateChecker(
            Context context
    ) {
        this.context = context;
    }

    // =========================================================
    // RUNTIME CAPABILITY
    // =========================================================

    public boolean canAccessMedia() {
        boolean result;
        if (Build.VERSION.SDK_INT >= 33) {
            result = hasPermission(android.Manifest.permission.READ_MEDIA_VIDEO);
        } else {
            result = hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!result){
            logs.systemWriter(strTAG, "canAccessMedia(false)");
            return false;
        }else {
            return true;
        }
    }

    public boolean canWriteSystemSettings() {
        boolean result = Settings.System.canWrite(context);
        if(!result){
            logs.systemWriter(strTAG, "canWriteSystemSettings(false)");
            return false;
        }else {
            return true;
        }
    }

    // =========================================================
    // TRUST SIGNAL
    // =========================================================

    public boolean isEnvironmentTrusted() {
        return true;
//        return !isDeveloperModeEnabled()
//                && !isAdbEnabled();
    }

    public boolean isHighRiskDevice() {

        return isDeveloperModeEnabled()
                || isAdbEnabled();
    }

    // DEVELOPER MODE =========================================================
    public boolean isDeveloperModeEnabled() {
        try {
            int value;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                value = Settings.Global.getInt(
                        context.getContentResolver(),
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                        0
                );
            } else {
                value = Settings.Secure.getInt(
                        context.getContentResolver(),
                        Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                        0
                );
            }
            boolean enabled = value == 1;
            logs.systemWriter(strTAG, "isDeveloperModeEnabled()" + enabled);
            return enabled;
        } catch (Exception e) {
            logs.systemWriter(strTAG, "isDeveloperModeEnabled()" + e.getMessage());
            return false;
        }
    }

    // ADB =========================================================
    public boolean isAdbEnabled() {
        try {
            Boolean value;
            value = Settings.Global.getInt(
                    context.getContentResolver(),
                    Settings.Global.ADB_ENABLED,
                    0
            ) == 1;
            logs.systemWriter(strTAG, "isAdbEnabled()" + value);
            return value;
        } catch (Exception e) {
            logs.systemWriter(strTAG, "isAdbEnabled()" + false);
            return false;
        }
    }

    // INTERNAL =========================================================
    private boolean hasPermission(String permission) {
        try {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            return false;
        }
    }

    // COMPATIBILITY =========================================================
    public boolean isSupportedEnvironment() {
        return Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.JELLY_BEAN;
    }
}