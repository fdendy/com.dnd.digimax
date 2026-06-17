package com.dnd.digimax.core.runtime.inspector;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.dnd.digimax.core.runtime.inspector.permission.PermissionStateChecker;
import com.dnd.digimax.core.runtime.inspector.recovery.RecoveryAction;
import com.dnd.digimax.core.runtime.inspector.recovery.RecoveryAdvisor;
import com.dnd.digimax.core.runtime.inspector.resource.CpuMonitor;
import com.dnd.digimax.core.runtime.inspector.resource.MemoryMonitor;
import com.dnd.digimax.core.runtime.inspector.resource.StorageMonitor;
import com.dnd.digimax.core.runtime.inspector.resource.ThermalMonitor;
import com.dnd.digimax.core.runtime.inspector.signal.EnvironmentDriftDetector;
import com.dnd.digimax.core.runtime.inspector.signal.TamperStateChecker;
import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

public class RuntimeInspector {

    private static final String TAG = "RuntimeInspector";


    // RESOURCE
    // =========================================================
    private final CpuMonitor cpu;
    private final MemoryMonitor memory;
    private final StorageMonitor storage;
    private final ThermalMonitor thermal;

    // =========================================================
    // SIGNAL
    // =========================================================

    private final EnvironmentDriftDetector drift;

    private final TamperStateChecker tamper;

    private final PermissionStateChecker permission;

    // =========================================================
    // RECOVERY
    // =========================================================

    private final RecoveryAdvisor recovery;

    // CONSTRUCTOR
    public RuntimeInspector(Context context) {
        cpu = new CpuMonitor();
        memory = new MemoryMonitor(context);
        storage = new StorageMonitor(context);
        thermal = new ThermalMonitor(context);
        drift = new EnvironmentDriftDetector(context);
        tamper = new TamperStateChecker(context);
        permission = new PermissionStateChecker(context);
        recovery = new RecoveryAdvisor();
    }

    // HEALTH
    public boolean isHealthy() {
        boolean healthy =
                cpu.isHealthy()
                        && memory.isHealthy()
                        && storage.isHealthy()
                        && thermal.isHealthy()
                        && drift.isEnvironmentStable();
        log(healthy);
        return healthy;
    }

    // TRUST
    public boolean isTrusted() {
        Log.d("isTrusted","permission.isEnvironmentTrusted() : "+permission.isEnvironmentTrusted());
        Log.d("isTampered","tamper.isTampered : "+tamper.isTampered());
        return permission.isEnvironmentTrusted() && !tamper.isTampered();
    }

    // CAPABILITY
    public boolean hasRuntimeCapability() {
        if(permission.canAccessMedia() && permission.canWriteSystemSettings()){
            return true;
        }else {
            Log.d(TAG,"hasRuntimeCapability(false)");
            return false;
        }
    }

    // CRITICAL FAILURE
    public boolean isCriticalFailure() {
        if(tamper.isTampered()){
            Log.d(TAG,"isCriticalFailure(TAMPERED)");
            return true;
        }else if(!thermal.isHealthy()){
            Log.d(TAG,"isCriticalFailure(THERMAL)");
            return true;
        }
        return false;

    }

    // RISK SCORE =========================================================
    public int getRiskScore() {
        int score = 0;
        if (!cpu.isHealthy()) score++;
        if (!memory.isHealthy()) score++;
        if (!storage.isHealthy()) score++;
        if (!thermal.isHealthy()) score += 2;
        if (!drift.isEnvironmentStable()) score++;
        if (!hasRuntimeCapability()) score++;
        return score;
    }

    // RECOVERY =========================================================
    public RecoveryAction getRecoveryAction() {
        return recovery.suggest(
                cpu,
                memory,
                storage,
                thermal,
                drift,
                tamper
        );
    }

    // SIGNAL =========================================================

    public boolean isTampered() {
        return tamper.isTampered();
    }

    public boolean isEnvironmentStable() {
        return drift.isEnvironmentStable();
    }

    // LOG =========================================================
    private long lastLogTime = 0;

    private void log(boolean health) {
        long now = System.currentTimeMillis();
        if (now - lastLogTime < 10000) {
            return;
        }
        lastLogTime = now;
        Log.d(
                TAG,
                "health=" + health +
                        " trusted=" + isTrusted() +
                        " capability=" + hasRuntimeCapability() +
                        " cpu=" + cpu.isHealthy() +
                        " mem=" + memory.isHealthy() +
                        " storage=" + storage.isHealthy() +
                        " thermal=" + thermal.isHealthy() +
                        " drift=" + drift.isEnvironmentStable() +
                        " tamper=" + tamper.isTampered()
        );
    }
}