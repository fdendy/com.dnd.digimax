package com.dnd.digimax.core.security.checker;

import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;

public class SystemWriteCheck {

    // =========================
    // MAIN ENTRY
    // =========================

    public boolean isSystemWriteSafe() {

        return isSystemPartitionReadOnly()
                && isProtectedWriteAllowedOnlyInAppSpace()
                && isNoRootWriteAccess();
    }

    // =========================
    // 1. SYSTEM PARTITION READ-ONLY CHECK
    // =========================

    private boolean isSystemPartitionReadOnly() {

        try {

            File system = new File("/system");

            return system.exists() && !canWriteTo(system);

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 2. APP SANDBOX WRITE VALIDATION
    // =========================

    private boolean isProtectedWriteAllowedOnlyInAppSpace() {

        try {

            File appDir = new File("/data/data");

            return appDir.exists() && canWriteToAppSandbox();

        } catch (Exception e) {
            return false;
        }
    }

    private boolean canWriteToAppSandbox() {

        try {

            File testFile = new File("/data/data/test_write_check.tmp");

            FileOutputStream fos = new FileOutputStream(testFile);
            fos.write("test".getBytes());
            fos.close();

            testFile.delete();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 3. ROOT WRITE ACCESS DETECTION
    // =========================

    private boolean isNoRootWriteAccess() {

        try {

            String[] paths = {
                    "/system/bin/su",
                    "/system/xbin/su",
                    "/sbin/su",
                    "/system/app/Superuser.apk"
            };

            for (String path : paths) {

                File f = new File(path);

                if (f.exists()) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // SAFE WRITE TEST
    // =========================

    private boolean canWriteTo(File file) {

        try {

            if (!file.exists()) return false;

            File test = new File(file, "write_test.tmp");

            FileOutputStream fos = new FileOutputStream(test);
            fos.write(1);
            fos.close();

            test.delete();

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}