package com.dnd.digimax.core.runtime.inspector.signal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

import java.io.BufferedReader;
import java.io.FileReader;

public class EnvironmentDriftDetector {
    private final String strTAG = "EnvironmentDriftDetector";
    private final LogsSystemWriter logs = new LogsSystemWriter();
    private final Context context;
    public EnvironmentDriftDetector(Context context) {
        this.context = context.getApplicationContext();
    }

    // MAIN ENTRY =========================================================
    public boolean isEnvironmentStable() {
        int score = getDriftScore();
        boolean stable = score <= 1;
        logs.systemWriter(strTAG,"EnvironmentStable="+ stable+ " score="+ score);
        return stable;
    }

    public boolean isUnsafe() {
        return getDriftScore() > 1;
    }

    // DRIFT SCORING =========================================================
    public int getDriftScore() {
        boolean debuggable = isDebuggableRuntime();
        boolean developer = isDeveloperOptionsEnabled();
        boolean suspicious = isSuspiciousRuntimeBehavior();
        boolean process = isRuntimeProcessDegraded();
        logs.systemWriter(strTAG,"debuggable=" + debuggable);
        logs.systemWriter(strTAG,"developerOptions=" + developer);
        logs.systemWriter(strTAG,"suspiciousRuntime=" + suspicious);
        logs.systemWriter(strTAG,"processDegraded=" + process);
        int score = 0;
        if (debuggable) score++; // security-sensitive
        if (developer) score++; // low severity
        if (suspicious) score += 2; // high severity
        if (process) score++; // weak signal
        logs.systemWriter(strTAG,"DriftScore=" + score);
        return score;
    }

    // DEBUG STATE =========================================================
    private boolean isDebuggableRuntime() {
        try {
            return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            logs.systemWriter(strTAG,"Debuggable check failed: " + e.getMessage());
            return false;
        }
    }

    // DEVELOPER OPTIONS =========================================================
    private boolean isDeveloperOptionsEnabled() {
        try {
            return Settings.Global.getInt(context.getContentResolver(),Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,0) == 1;
        } catch (Exception e) {
            logs.systemWriter(strTAG,"Developer options check failed: "+ e.getMessage());
            return false;
        }
    }

    // RUNTIME HOOK DETECTION =========================================================
    private boolean isSuspiciousRuntimeBehavior() {
        final String[] suspiciousKeywords = {
                "frida",
                "xposed",
                "substrate",
                "lsposed",
                "edxp",
                "zygisk",
                "riru",
                "magisk"
        };

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     "/proc/self/maps"
                             ))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String lower = line.toLowerCase();
                for (String keyword : suspiciousKeywords) {
                    if (lower.contains(keyword)) {
                        logs.systemWriter(strTAG,
                                "Suspicious runtime: "
                                        + line
                        );

                        return true;
                    }
                }
            }

        } catch (Exception e) {

            logs.systemWriter(
                    strTAG,
                    "Runtime scan failed: "
                            + e.getMessage()
            );

            return false;
        }

        return false;
    }

    // PROCESS HEALTH =========================================================
    private boolean isRuntimeProcessDegraded() {
        try {
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return false;
            }
            int pid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo p : am.getRunningAppProcesses()) {
                if (p.pid == pid) {
                    return p.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE;
                }
            }
        } catch (Exception e) {
            logs.systemWriter(strTAG,"Process health failed: "+ e.getMessage());
        }
        return false;
    }
}