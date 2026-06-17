package com.dnd.digimax.core.runtime.inspector.resource;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

/**
 * ThermalMonitor
 *
 * Fungsi:
 * - Membaca kondisi thermal / suhu device secara abstrak
 * - Mengubah status thermal menjadi level sistem
 * - Digunakan oleh RuntimeInspector & RecoveryAdvisor
 *
 * Catatan:
 * - Android tidak selalu memberikan suhu CPU langsung
 * - Jadi kita gunakan PowerManager + heuristic sederhana
 */
public class ThermalMonitor {
    private String strTAG = "CpuMonitor";
    private LogsSystemWriter logs = new LogsSystemWriter();
    private final PowerManager powerManager;

    public ThermalMonitor(Context context) {
        this.powerManager =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }

    /**
     * Thermal level estimation (0 - 100)
     *
     * Catatan:
     * - Ini bukan suhu Celsius
     * - Ini representasi severity thermal system
     */
    public float getThermalLevel() {

        float base = 35f;

        // API 29+ bisa detect overheating hint
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (powerManager != null && powerManager.isDeviceIdleMode()) {
                base -= 10f;
            }
        }

        // heuristic fallback (device load approximation)
        if (powerManager != null && powerManager.isPowerSaveMode()) {
            base += 15f;
        }

        return clamp(base, 0f, 100f);
    }

    /**
     * THERMAL HEALTH CHECK
     * Dipakai RuntimeInspector / RecoveryAdvisor gate
     */
    public boolean isHealthy() {
        if(getThermalLevel() > 70f)logs.systemWriter(strTAG, "THERMAL HIGH");
        return getThermalLevel() <= 70f;
    }

    /**
     * Thermal state classification
     */
    public ThermalState getState() {
        float level = getThermalLevel();

        if (level >= 85f) return ThermalState.CRITICAL;
        if (level >= 70f) return ThermalState.DEGRADED;
        if (level >= 50f) return ThermalState.WARNING;
        return ThermalState.NORMAL;
    }

    /**
     * Simple overheating check
     */
    public boolean isOverheating() {
        return getThermalLevel() >= 85f;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public enum ThermalState {
        NORMAL,
        WARNING,
        DEGRADED,
        CRITICAL
    }
}