package com.dnd.digimax.core.security.detector;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.dnd.digimax.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;

public class EmulatorDetector {
    public static boolean isDeviceSafe(Context c, String pkg, String sigHash) {
        boolean root = isRooted();
        boolean emulator = isEmulator();
        boolean frida = isFrida();
        boolean xposed = isXposed();
        boolean pkgValid = isPackageSame(c, pkg);
        boolean sigValid = isSignatureSame(c, sigHash);

        Log.e("SECURITY",
                "root=" + root +
                        " emulator=" + emulator +
                        " frida=" + frida +
                        " xposed=" + xposed +
                        " pkg=" + pkgValid +
                        " sig=" + sigValid
        );

        // 🔥 IMPORTANT: allow emulator for debug
        if (BuildConfig.DEBUG) {
            return true;
        }

        return !root
                && !emulator
                && !frida
                && !xposed
                && pkgValid
                && sigValid;
    }

    // ================= ROOT =================
    public static boolean isRooted() {
        String[] paths = {
                "/system/xbin/su",
                "/system/bin/su",
                "/sbin/su"
        };

        for (String p : paths) {
            if (new File(p).exists()) {
                Log.e("SECURITY", "Root file detected: " + p);
                return true;
            }
        }

        return false;
    }

    // ================= EMULATOR =================
    public static boolean isEmulator() {
        boolean result =
                Build.FINGERPRINT.startsWith("generic") ||
                        Build.MODEL.toLowerCase().contains("emulator") ||
                        Build.HARDWARE.contains("goldfish") ||
                        Build.HARDWARE.contains("ranchu");

        Log.e("SECURITY", "isEmulator=" + result);
        return result;
    }

    // ================= FRIDA =================
    public static boolean isFrida() {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("/proc/self/maps"));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("frida")) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    // ================= XPOSED =================
    public static boolean isXposed() {
        try {
            throw new Exception();
        } catch (Exception e) {
            for (StackTraceElement el : e.getStackTrace()) {
                if (el.getClassName().contains("XposedBridge")) {
                    return true;
                }
            }
        }
        return false;
    }

    // ================= PACKAGE =================
    public static boolean isPackageSame(Context c, String pkg) {
        return c.getPackageName().equals(pkg);
    }

    // ================= SIGNATURE =================
    public static boolean isSignatureSame(Context c, String expected) {
        try {
            PackageInfo pi;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pi = c.getPackageManager().getPackageInfo(
                        c.getPackageName(),
                        PackageManager.GET_SIGNING_CERTIFICATES
                );

                for (Signature sig : pi.signingInfo.getApkContentsSigners()) {
                    if (hash(sig).equals(expected)) return true;
                }

            } else {
                pi = c.getPackageManager().getPackageInfo(
                        c.getPackageName(),
                        PackageManager.GET_SIGNATURES
                );

                for (Signature sig : pi.signatures) {
                    if (hash(sig).equals(expected)) return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    private static String hash(Signature sig) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(sig.toByteArray());
        return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
    }
}