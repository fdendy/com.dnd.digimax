package com.dnd.digimax.core.device.storage;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class StorageManager {

    private File getPath() {
        return Environment.getDataDirectory(); // internal storage
    }

    // =========================
    // BASIC INFO
    // =========================

    public long getTotalBytes() {
        try {
            StatFs stat = new StatFs(getPath().getPath());
            return stat.getTotalBytes();
        } catch (Exception e) {
            return -1;
        }
    }

    public long getFreeBytes() {
        try {
            StatFs stat = new StatFs(getPath().getPath());
            return stat.getAvailableBytes();
        } catch (Exception e) {
            return -1;
        }
    }

    public long getUsedBytes() {
        try {
            long total = getTotalBytes();
            long free = getFreeBytes();

            if (total < 0 || free < 0) return -1;

            return total - free;
        } catch (Exception e) {
            return -1;
        }
    }

    // =========================
    // PERCENTAGE
    // =========================

    public int getUsagePercent() {
        try {
            long total = getTotalBytes();
            long used = getUsedBytes();

            if (total <= 0) return -1;

            return (int) ((used / (float) total) * 100);
        } catch (Exception e) {
            return -1;
        }
    }

    // =========================
    // STATUS CHECK
    // =========================

    public boolean isLowStorage() {
        int percent = getUsagePercent();
        if (percent < 0) return false;
        return percent >= 90; // threshold bisa disesuaikan
    }

    public boolean isCritical() {
        int percent = getUsagePercent();
        if (percent < 0) return false;
        return percent >= 95;
    }

    // =========================
    // FORMAT HELPER
    // =========================
    public String formatSize(long bytes) {
        if (bytes < 0) return "N/A";

        float kb = bytes / 1024f;
        float mb = kb / 1024f;
        float gb = mb / 1024f;

        if (gb >= 1) return String.format("%.2f GB", gb);
        if (mb >= 1) return String.format("%.2f MB", mb);
        if (kb >= 1) return String.format("%.2f KB", kb);

        return bytes + " B";
    }
}