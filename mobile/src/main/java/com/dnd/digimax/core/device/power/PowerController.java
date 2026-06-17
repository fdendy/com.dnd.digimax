package com.dnd.digimax.core.device.power;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.view.WindowManager;

public class PowerController {

    private final Context context;
    private PowerManager.WakeLock wakeLock;

    public PowerController(Context context) {
        this.context = context.getApplicationContext();
    }

    // =========================
    // KEEP SCREEN ON (UI LEVEL)
    // =========================

    public void keepScreenOn(Activity activity) {
        try {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        } catch (Exception ignored) {
        }
    }

    public void allowScreenOff(Activity activity) {
        try {
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        } catch (Exception ignored) {
        }
    }

    // =========================
    // WAKE SCREEN (WakeLock)
    // =========================

    public void wakeUp() {
        try {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

            if (wakeLock == null || !wakeLock.isHeld()) {
                wakeLock = pm.newWakeLock(
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.ON_AFTER_RELEASE,
                        "digimax:wakelock"
                );

                wakeLock.acquire(5000); // auto release 5 detik
            }

        } catch (Exception ignored) {
        }
    }

    // =========================
    // SLEEP SCREEN (BEST EFFORT)
    // =========================

    public void sleep(Activity activity) {
        try {
            // Cara paling aman (UI level)
            allowScreenOff(activity);

            // Optional: turunin brightness (kalau mau)
            activity.getWindow().setAttributes(
                    activity.getWindow().getAttributes()
            );

        } catch (Exception ignored) {
        }
    }

    // =========================
    // CHECK STATE
    // =========================

    public boolean isScreenOn() {
        try {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return pm.isInteractive();
        } catch (Exception e) {
            return true;
        }
    }

    // =========================
    // RELEASE
    // =========================

    public void releaseWakeLock() {
        try {
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }
        } catch (Exception ignored) {
        }
    }
}