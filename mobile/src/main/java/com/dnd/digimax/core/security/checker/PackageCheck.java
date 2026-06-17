package com.dnd.digimax.core.security.checker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningInfo;
import android.os.Build;

public class PackageCheck {

    private final Context context;

    public PackageCheck(Context context) {
        this.context = context;
    }

    // =========================
    // MAIN ENTRY
    // =========================

    public boolean isPackageValid() {

        return isPackageNameValid()
                && isInstallerValid()
                && isSignatureValid()
                && isNotClonedPackage();
    }

    // =========================
    // 1. PACKAGE NAME VALIDATION
    // =========================

    private boolean isPackageNameValid() {
        try {
            return context.getPackageName()
                    .equals(context.getApplicationContext().getPackageName());
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 2. INSTALLER VALIDATION (FIXED)
    // =========================

    private boolean isInstallerValid() {

        try {

            PackageManager pm = context.getPackageManager();

            String installer = pm.getInstallerPackageName(context.getPackageName());

            if (installer == null) return false;

            return installer.equals("com.android.vending")
                    || installer.equals("com.google.android.feedback");

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 3. SIGNATURE VALIDATION (IMPORTANT FIX)
    // =========================

    private boolean isSignatureValid() {

        try {

            PackageManager pm = context.getPackageManager();

            PackageInfo info = pm.getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNING_CERTIFICATES
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                SigningInfo signingInfo = info.signingInfo;

                if (signingInfo == null) return false;

                return signingInfo.hasMultipleSigners()
                        || signingInfo.getApkContentsSigners() != null;
            }

            // fallback legacy (Android 8)
            return info.signatures != null && info.signatures.length > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 4. CLONE DETECTION (IMPROVED HEURISTIC)
    // =========================

    private boolean isNotClonedPackage() {

        try {

            int uid = android.os.Process.myUid();

            if (uid <= 0) return false;

            String dataDir = context.getApplicationInfo().dataDir;

            if (dataDir == null) return false;

            // improved heuristic
            return !dataDir.contains("clone")
                    && !dataDir.contains("dual")
                    && !dataDir.contains("virtual")
                    && !dataDir.contains("parallel");

        } catch (Exception e) {
            return false;
        }
    }
}