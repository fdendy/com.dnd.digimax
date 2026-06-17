package com.dnd.digimax.core.security.verifier;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import java.security.MessageDigest;

public class SignatureValidator {
    public boolean validate(Context context, String expectedHash) {
        if (expectedHash == null || expectedHash.isEmpty()) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(
                            context.getPackageName(),
                            PackageManager.GET_SIGNING_CERTIFICATES
                    );
            Signature[] signatures = getSignatures(packageInfo);
            if (signatures == null || signatures.length == 0) {
                return false;
            }
            for (Signature signature : signatures) {
                String currentHash = sha256(signature.toByteArray());
                if (currentHash != null && currentHash.equalsIgnoreCase(expectedHash.trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    // SAFE SIGNATURE EXTRACTOR =========================
    private Signature[] getSignatures(PackageInfo packageInfo) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (packageInfo.signingInfo == null) {
                    return null;
                }
                return packageInfo.signingInfo.getApkContentsSigners();
            }
            // fallback Android 8–8.1
            return packageInfo.signatures;
        } catch (Exception e) {
            return null;
        }
    }

    // HASH GENERATOR =========================
    private String sha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02X", b));
            }
            return hex.toString();
        } catch (Exception e) {
            return null;
        }
    }

    // OPTIONAL HELPER (FOR DEBUGGING / BUILD PIPELINE) =========================
    public String getAppSignatureHash(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(
                            context.getPackageName(),
                            PackageManager.GET_SIGNING_CERTIFICATES
                    );
            Signature[] signatures = getSignatures(packageInfo);
            if (signatures != null && signatures.length > 0) {
                return sha256(signatures[0].toByteArray());
            }
        } catch (Exception ignored) {}
        return null;
    }
}