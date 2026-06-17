package com.dnd.digimax.core.runtime.capability;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.dnd.digimax.core.runtime.inspector.resource.MemoryMonitor;
import com.dnd.digimax.core.runtime.inspector.resource.StorageMonitor;
import com.dnd.digimax.core.system.SystemInfoProvider;

/**
 * Evaluates device capability score for cluster decision making.
 * PURE SCORING ENGINE (NO DIRECT OS LOGIC)
 */
public class OSCapabilityResolver {

    private final Context context;
    private final SystemInfoProvider systemInfo;
    private MemoryMonitor memoryMonitor;
    private StorageMonitor storageMonitor;

    private int cachedScore = -1;

    public OSCapabilityResolver(
            Context context,
            SystemInfoProvider systemInfo
    ) {
        this.context = context;
        this.systemInfo = systemInfo;
    }

    // =========================
    // MAIN ENTRY
    // =========================

    private final Object lock = new Object();

    public int getCapabilityScore() {
        synchronized (lock) {
            if (cachedScore != -1) return cachedScore;

            int score = 0;
            score += evaluateCpuTier();
            score += evaluateMemory();
            score += evaluateStorage();
            score += evaluateOs();
            score += evaluateIntegritySignals();

            cachedScore = score;
            return score;
        }
    }
    // =========================
    // HARDWARE SIGNALS
    // =========================

    private int evaluateCpuTier() {

        int cores = Runtime.getRuntime().availableProcessors();

        if (cores >= 8) return 30;
        if (cores >= 4) return 20;
        return 10;
    }

    private int evaluateMemory() {
        float totalMem = memoryMonitor.getTotalMemoryMB();
        if (totalMem >= 6000) return 30;
        if (totalMem >= 3000) return 20;
        return 10;
    }

    private int evaluateStorage() {
        float free = storageMonitor.getAvailableStorageMB();
        if (free >= 20000) return 20;
        if (free >= 10000) return 10;
        return 5;
    }

    // =========================
    // OS SIGNALS
    // =========================

    private int evaluateOs() {

        int sdk = Build.VERSION.SDK_INT;

        if (sdk >= 30) return 10;
        if (sdk >= 26) return 7;
        return 3;
    }

    // =========================
    // SECURITY SIGNALS (FROM SYSTEMINFO)
    // =========================

    private int evaluateIntegritySignals() {

        int penalty = 0;

        if (systemInfo.isEmulator()) penalty -= 30;
        if (systemInfo.isRooted()) penalty -= 20;
        if (systemInfo.isDebuggableBuild()) penalty -= 10;

        return Math.max(penalty, -40);
    }

    // =========================
    // CONTROL
    // =========================

    public void reset() {
        cachedScore = -1;
    }

    public void dump() {
        Log.d("OSCapability",
                "score=" + cachedScore +
                        ", sdk=" + Build.VERSION.SDK_INT +
                        ", cores=" + Runtime.getRuntime().availableProcessors()
        );
    }
}