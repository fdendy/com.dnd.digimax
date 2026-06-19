package com.dnd.digimax.core.security.coordinator;

import android.content.Context;
import android.util.Log;

import com.dnd.digimax.BuildConfig;
import com.dnd.digimax.core.security.checker.BuildCheck;
import com.dnd.digimax.core.security.checker.DebuggerCheck;
import com.dnd.digimax.core.security.checker.FridaCheck;
import com.dnd.digimax.core.security.detector.EmulatorDetector;
import com.dnd.digimax.core.security.verifier.SignatureValidator;

public class SecurityCoordinator {

    private static final String TAG = "SecurityCoordinator";

    private final Context context;

    private final SignatureValidator signatureValidator;
    private final DebuggerCheck debuggerCheck;
    private final FridaCheck fridaCheck;
    private final BuildCheck buildCheck;

    public SecurityCoordinator(Context context) {
        this.context = context.getApplicationContext();
        this.signatureValidator = new SignatureValidator();
        this.debuggerCheck = new DebuggerCheck();
        this.fridaCheck = new FridaCheck();
        this.buildCheck = new BuildCheck(context);
    }

    // ENTRY POINT
    public boolean checkStartupIntegrity() {

        if (BuildConfig.DEBUG) {
            Log.w(TAG, "Startup integrity checks bypassed for debug build");
            return true;
        }

        // 🔥 1. SIGNATURE (PALING KRITIS)
        if (!checkSignature()) {
            return failFast("SIGNATURE_INVALID");
        }

        // 🔥 2. TAMPER / HOOK / DEBUG
        if (isRuntimeCompromised()) {
            return failFast("RUNTIME_COMPROMISED");
        }

        // 🔥 3. ENVIRONMENT
        if (!buildCheck.isBuildValid()) {
            return failFast("INVALID_BUILD");
        }
        return true;
    }

    // CHECK GROUPS
    private boolean checkSignature() {
        return signatureValidator.validate(
                context,
                getExpectedSignatureHash()
        );
    }
    private boolean isRuntimeCompromised() {
        return EmulatorDetector.isEmulator()
                || debuggerCheck.isDebuggerPresent()
                || fridaCheck.isFridaDetected();
    }
    private String getExpectedSignatureHash() {
        String CERT_0 = "9CF427AE0C51F32BD38";
        String CERT_1 = "CEB18287FAAB325DEF1";
        String CERT_2 = "6E8E6C2AE7E978581F990742DD";
        String expectedCert = CERT_0+CERT_1+CERT_2;


        // ⚠️ ideal: BuildConfig.SIGNATURE_HASH
        return expectedCert;
    }

    // FAIL STRATEGY (SAFE) =========================
    private boolean failFast(String reason) {
        Log.e(TAG, "SECURITY VIOLATION: " + reason);
        return false;
    }
}
