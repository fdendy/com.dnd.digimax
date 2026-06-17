package com.dnd.digimax.core.runtime.inspector.resource;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

/**
 * MemoryMonitor
 *
 * Fungsi:
 * - Mengambil status memory device
 * - Memberikan insight penggunaan RAM aplikasi + system
 * - Dipakai oleh RuntimeInspector & RecoveryAdvisor
 */
public class MemoryMonitor {
    private String strTAG = "MemoryMonitor";
    private LogsSystemWriter logs = new LogsSystemWriter();

    private final ActivityManager activityManager;

    public MemoryMonitor(Context context) {
        this.activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * Return total memory device (MB)
     */
    public float getTotalMemoryMB() {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);

        return info.totalMem / (1024f * 1024f);
    }

    /**
     * Return available memory device (MB)
     */
    public float getAvailableMemoryMB() {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);

        return info.availMem / (1024f * 1024f);
    }

    /**
     * Return usage percentage (0 - 100)
     */
    public float getMemoryUsage() {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);

        float used = info.totalMem - info.availMem;
        return (used / info.totalMem) * 100f;
    }

    /**
     * App heap usage (debug level insight)
     */
    public float getAppHeapUsedMB() {
        return Debug.getNativeHeapAllocatedSize() / (1024f * 1024f);
    }

    /**
     * Check apakah system low memory state
     */
    public boolean isLowMemory() {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);

        return info.lowMemory;
    }

    /**
     * NEW: Health gate for boot/runtime decision
     *
     * TRUE  -> memory masih aman
     * FALSE -> memory sudah tidak stabil
     */
    public boolean isHealthy() {
        float usage = getMemoryUsage();
        if(usage>80f)logs.systemWriter(strTAG, "RAM LOW"+ usage);
        // threshold dibuat sedikit konservatif untuk kiosk / cluster device
        return usage >= 0f && usage <= 80f && !isLowMemory();
    }

    /**
     * Quick health category
     */
    public MemoryState getState() {
        float usage = getMemoryUsage();
        if (usage > 90) return MemoryState.CRITICAL;
        if (usage > 75) return MemoryState.DEGRADED;
        if (usage > 60) return MemoryState.WARNING;
        return MemoryState.STABLE;
    }

    public enum MemoryState {
        STABLE,
        WARNING,
        DEGRADED,
        CRITICAL
    }
}