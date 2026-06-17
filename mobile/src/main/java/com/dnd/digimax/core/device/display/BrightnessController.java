package com.dnd.digimax.core.device.display;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class BrightnessController {

    private final Context context;

    public BrightnessController(Context context) {
        this.context = context;
    }

    // =========================
    // GET
    // =========================

    public int getBrightnessPercent() {
        try {
            int brightness = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );

            return (int) ((brightness / 255f) * 100);

        } catch (Exception e) {
            return -1;
        }
    }

    public boolean isAutoBrightness() {
        try {
            int mode = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
            );

            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // SET (SYSTEM)
    // =========================

    public boolean setBrightnessPercent(int percent) {
        try {
            percent = clamp(percent, 0, 100);

            if (!Settings.System.canWrite(context)) {
                return false;
            }

            int value = (int) (percent / 100f * 255);

            ContentResolver resolver = context.getContentResolver();

            Settings.System.putInt(
                    resolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            );

            Settings.System.putInt(
                    resolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    value
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // FALLBACK (ACTIVITY)
    // =========================

    public void setBrightnessForActivity(Activity activity, int percent) {
        try {
            percent = clamp(percent, 0, 100);

            float value = percent / 100f;

            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();

            params.screenBrightness = value;
            window.setAttributes(params);

        } catch (Exception ignored) {
        }
    }

    // =========================
    // AUTO MODE
    // =========================

    public boolean setAutoBrightness(boolean enabled) {
        try {
            if (!Settings.System.canWrite(context)) {
                return false;
            }

            Settings.System.putInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    enabled
                            ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                            : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // UTIL
    // =========================

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(val, max));
    }
}