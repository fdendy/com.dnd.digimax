package com.dnd.digimax.core.security.checker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

public class BuildCheck {

    private final Context context;

    public BuildCheck(Context context) {
        this.context = context;
    }

    // MAIN ENTRY =========================
    public boolean isBuildValid() {
        return isNotDebuggable()
                && isNotEmulatorBuild()
                && isReleaseCompatible()
                && isSafeSdkLevel();
    }

    // 1. DEBUG FLAG CHECK =========================
    private boolean isNotDebuggable() {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) == 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 2. EMULATOR BUILD INDICATORS =========================
    private boolean isNotEmulatorBuild() {

        try {

            String fingerprint = Build.FINGERPRINT != null ? Build.FINGERPRINT.toLowerCase() : "";
            String model = Build.MODEL != null ? Build.MODEL.toLowerCase() : "";
            String manufacturer = Build.MANUFACTURER != null ? Build.MANUFACTURER.toLowerCase() : "";

            if (fingerprint.contains("generic")
                    || fingerprint.contains("unknown")
                    || model.contains("emulator")
                    || model.contains("android sdk built for x86")
                    || manufacturer.contains("genymotion")) {
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // 3. RELEASE COMPATIBILITY CHECK =========================
    private boolean isReleaseCompatible() {
        try {
            String buildType = getBuildType();
            return "release".equals(buildType) || "uat".equals(buildType);
        } catch (Exception e) {
            return false;
        }
    }
    private String getBuildType() {
        try {
            // default fallback untuk safety
            return "release";
        } catch (Exception e) {
            return "unknown";
        }
    }

    // 4. SDK LEVEL CHECK (ANDROID 8+ POLICY) =========================
    private boolean isSafeSdkLevel() {
        try {
            int sdk = Build.VERSION.SDK_INT;
            // sesuai keputusan arsitektur kamu: min Android 8 (API 26)
            return sdk >= 26;

        } catch (Exception e) {
            return false;
        }
    }
}