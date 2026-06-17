package com.dnd.digimax.core.security.checker;

import java.io.BufferedReader;
import java.io.FileReader;

public class FridaCheck {

    // =========================
    // MAIN ENTRY
    // =========================

    public boolean isFridaDetected() {

        return isFridaProcessRunning()
                || isFridaLibraryInjected()
                || isSuspiciousMemoryMap();
    }

    // =========================
    // 1. PROCESS NAME CHECK
    // =========================

    private boolean isFridaProcessRunning() {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader("/proc/self/cmdline"));

            String line = reader.readLine();

            reader.close();

            if (line == null) return false;

            line = line.toLowerCase();

            return line.contains("frida")
                    || line.contains("gadget")
                    || line.contains("gum-js-loop");

        } catch (Exception e) {
            return true;
        }
    }

    // =========================
    // 2. MEMORY MAP SCAN
    // =========================

    private boolean isFridaLibraryInjected() {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader("/proc/self/maps"));

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.toLowerCase();

                if (line.contains("frida")
                        || line.contains("gadget")
                        || line.contains("xposed")
                        || line.contains("substrate")) {

                    reader.close();
                    return true;
                }
            }

            reader.close();

        } catch (Exception e) {
            return true;
        }

        return false;
    }

    // =========================
    // 3. SUSPICIOUS MEMORY BEHAVIOR
    // =========================

    private boolean isSuspiciousMemoryMap() {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader("/proc/self/maps"));

            String line;

            int suspiciousCount = 0;

            while ((line = reader.readLine()) != null) {

                // frida biasanya inject anonymous executable memory
                if (line.contains("rwxp") && line.contains("anon")) {
                    suspiciousCount++;
                }
            }

            reader.close();

            // heuristic threshold
            return suspiciousCount > 5;

        } catch (Exception e) {
            return true;
        }
    }
}