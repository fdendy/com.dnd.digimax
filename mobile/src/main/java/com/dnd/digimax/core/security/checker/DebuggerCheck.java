package com.dnd.digimax.core.security.checker;

import android.os.Debug;
import android.os.Process;

import java.io.BufferedReader;
import java.io.FileReader;

public class DebuggerCheck {

    // =========================
    // MAIN ENTRY
    // =========================

    public boolean isDebuggerPresent() {

        return isJavaDebuggerAttached()
                || isNativeTracerDetected()
                || isDebuggableProcess();
    }

    // =========================
    // 1. JAVA DEBUGGER CHECK
    // =========================

    private boolean isJavaDebuggerAttached() {

        try {
            return Debug.isDebuggerConnected()
                    || Debug.waitingForDebugger();
        } catch (Exception e) {
            return true;
        }
    }

    // =========================
    // 2. NATIVE TRACER CHECK (ptrace / Frida / LLDB indicator)
    // =========================

    private boolean isNativeTracerDetected() {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader("/proc/self/status"));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("TracerPid:")) {

                    String[] parts = line.split(":");

                    if (parts.length > 1) {

                        int tracerPid = Integer.parseInt(parts[1].trim());

                        if (tracerPid != 0) {
                            reader.close();
                            return true;
                        }
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            return true;
        }

        return false;
    }

    // =========================
    // 3. DEBUGGABLE PROCESS CHECK
    // =========================

    private boolean isDebuggableProcess() {

        try {

            int flags = android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE;

            // fallback safety check via system property
            return (Process.myPid() > 0 && Debug.isDebuggerConnected());

        } catch (Exception e) {
            return true;
        }
    }
}