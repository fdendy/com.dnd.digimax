package com.dnd.digimax.core.runtime.inspector.signal;

import android.content.Context;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.attribute.FileAttribute;
import java.security.MessageDigest;

public class TamperStateChecker {
    private String strTAG = "TamperStateChecker";
    private LogsSystemWriter logs = new LogsSystemWriter();

    private final Context context;

    public TamperStateChecker(Context context) {
        this.context = context;
    }

    // =========================
    // MAIN CHECK
    // =========================

    public boolean isTampered() {
//        logs.systemWriter(strTAG, "isTampered(isApkSignatureNotValid)"+ isApkSignatureNotValid());
//        logs.systemWriter(strTAG, "isTampered(isNativeLibTampered)"+ isNativeLibTampered());
//        logs.systemWriter(strTAG, "isTampered(isCodeIntegrityBroken)"+ isCodeIntegrityBroken());
        return isApkSignatureNotValid()
                || isNativeLibTampered()
                || isCodeIntegrityBroken();
    }

    // 1. APK SIGNATURE RUNTIME CHECK =========================
    private boolean isApkSignatureNotValid(){
        return false;
//        try {
//            String currentHash = getApkHash();
//            String expectedHash = getExpectedHash();
//            return !currentHash.equals(expectedHash);
//        } catch (Exception e) {
//            return true;
//        }
    }
    // 2. APK FILE HASH CHECK =========================
    private String getApkHash() {
        try {
            String apkPath = context.getPackageCodePath();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(new File(apkPath));
            byte[] buffer = new byte[1024];
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

        // HARUS diganti ke:
        // - server-side validation
        // - build-time injected hash
        return "EXPECTED_APP_HASH";
    }
    // 3. NATIVE LIB CHECK (HOOK DETECTION LIGHT) =========================
    private boolean isNativeLibTampered() {
        final String[] suspiciousKeywords = {
                "frida",
                "xposed",
                "substrate",
                "edxp",
                "lsposed",
                "zygisk",
                "riru",
                "magisk",
                "frida-agent",
                "frida-server"
        };

        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/self/maps"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String lower = line.toLowerCase();
                for (String keyword : suspiciousKeywords) {
                    if (lower.contains(keyword)) {
                        logs.systemWriter(strTAG,"Native tamper detected: " + line);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logs.systemWriter(strTAG,"maps scan failed: " + e.getMessage());
            return false;
        }
        return false;
    }
    // 4. CODE INTEGRITY HEURISTIC =========================
    private boolean isCodeIntegrityBroken() {
        try {
            String classPath = System.getProperty("java.class.path");
            if (classPath == null) {
                return false;
            }
            String lower = classPath.toLowerCase();
            final String[] suspicious = {
                    "frida",
                    "xposed",
                    "substrate",
                    "lsposed",
                    "edxp"
            };
            for (String keyword : suspicious) {
                if (lower.contains(keyword)) {
                    logs.systemWriter(strTAG, "Suspicious classpath: " + classPath);
                    return true;
                }
            }
        } catch (Exception e) {
            logs.systemWriter(strTAG, "Code integrity check failed: " + e.getMessage());
            return false;
        }

        return false;
    }
}