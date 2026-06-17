package com.dnd.digimax.core.security.checker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class BinaryCheck {

    private final Context context;

    public BinaryCheck(Context context) {
        this.context = context;
    }

    // =========================
    // MAIN ENTRY
    // =========================

    public boolean isBinaryIntact() {

        return isApkFilePresent()
                && isApkHashValid()
                && isDexStructureValid();
    }

    // =========================
    // 1. APK EXISTENCE CHECK
    // =========================

    private boolean isApkFilePresent() {

        try {

            String apkPath = context.getPackageCodePath();
            File apk = new File(apkPath);

            return apk.exists() && apk.length() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // 2. APK HASH VALIDATION
    // =========================

    private boolean isApkHashValid() {

        try {

            String currentHash = calculateApkHash();
            String expectedHash = getExpectedHash();

            return currentHash.equals(expectedHash);

        } catch (Exception e) {
            return false;
        }
    }

    private String calculateApkHash() {

        try {

            String apkPath = context.getPackageCodePath();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            FileInputStream fis = new FileInputStream(new File(apkPath));

            byte[] buffer = new byte[4096];
            int read;

            while ((read = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }

            fis.close();

            byte[] hash = digest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            return "";
        }
    }

    private String getExpectedHash() {

        // ⚠️ HARUS DIGANTI BUILD-TIME / SERVER-INJECTED VALUE
        return "EXPECTED_APK_HASH";
    }

    // =========================
    // 3. DEX STRUCTURE VALIDATION (LIGHT HEURISTIC)
    // =========================

    private boolean isDexStructureValid() {

        try {

            // simple sanity check: classes.dex harus ada
            String apkPath = context.getPackageCodePath();

            java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(apkPath);

            boolean hasDex = zipFile.getEntry("classes.dex") != null;

            zipFile.close();

            return hasDex;

        } catch (Exception e) {
            return false;
        }
    }
}