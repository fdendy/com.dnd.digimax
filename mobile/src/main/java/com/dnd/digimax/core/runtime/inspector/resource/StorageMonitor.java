package com.dnd.digimax.core.runtime.inspector.resource;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

import java.io.File;

/**
 * StorageMonitor
 *
 * Context-based + self-resolving
 * - No external dependency on File
 * - Safe for all Android versions
 * - Fail-safe (no crash)
 */
public class StorageMonitor {
    private String strTAG = "StorageMonitor";
    private LogsSystemWriter logs = new LogsSystemWriter();


    private final File dataDir;

    public StorageMonitor(Context context) {
        this.dataDir = resolveDir(context);
    }

    // =========================
    // SAFE DIR RESOLUTION
    // =========================

    private File resolveDir(Context context) {

        try {
            File dir = context.getFilesDir();
            if (dir != null && dir.exists()) return dir;
        } catch (Exception ignored) {}

        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.exists()) return dir;
        } catch (Exception ignored) {}

        try {
            File dir = Environment.getDataDirectory();
            if (dir != null && dir.exists()) return dir;
        } catch (Exception ignored) {}

        // last fallback (should never fail)
        return new File("/");
    }

    // =========================
    // INTERNAL SAFE STAT
    // =========================

    private StatFs getStat() {
        try {
            return new StatFs(dataDir.getAbsolutePath());
        } catch (Exception e) {
            return null;
        }
    }

    // =========================
    // STORAGE METRICS
    // =========================

    public float getTotalStorageMB() {
        StatFs stat = getStat();
        if (stat == null) return 0f;

        long total = stat.getBlockSizeLong() * stat.getBlockCountLong();
        return total / (1024f * 1024f);
    }

    public float getAvailableStorageMB() {
        StatFs stat = getStat();
        if (stat == null) return 0f;

        long avail = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        return avail / (1024f * 1024f);
    }

    public float getUsedStorageMB() {
        float total = getTotalStorageMB();
        float avail = getAvailableStorageMB();
        return total - avail;
    }

    public float getStoragePressure() {
        float total = getTotalStorageMB();
        if (total <= 0) return 0f;

        float used = getUsedStorageMB();
        return (used / total) * 100f;
    }

    // =========================
    // HEALTH
    // =========================

    public boolean isLowStorage() {
        return getStoragePressure() > 85f;
    }

    public boolean isHealthy() {
        if(getState() == StorageState.DEGRADED || getState() == StorageState.CRITICAL)logs.systemWriter(strTAG, "Storage LOW)");;
        return getState() == StorageState.STABLE
                || getState() == StorageState.WARNING;
    }

    // =========================
    // STATE CLASSIFICATION
    // =========================

    public StorageState getState() {
        float pressure = getStoragePressure();
        if (pressure > 95f) return StorageState.CRITICAL;
        if (pressure > 85f) return StorageState.DEGRADED;
        if (pressure > 70f) return StorageState.WARNING;
        return StorageState.STABLE;
    }

    public enum StorageState {
        STABLE,
        WARNING,
        DEGRADED,
        CRITICAL
    }
}