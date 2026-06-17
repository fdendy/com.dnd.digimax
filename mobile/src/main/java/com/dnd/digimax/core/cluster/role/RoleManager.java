package com.dnd.digimax.core.cluster.role;

import android.util.Log;

import com.dnd.digimax.core.runtime.capability.OSCapabilityResolver;
import com.dnd.digimax.core.system.SystemInfoProvider;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Central authority for cluster role decision.
 * Single source of truth for MASTER / WORKER state.
 */
public class RoleManager {

    public enum Role {
        MASTER,
        WORKER,
        UNKNOWN
    }

    private final OSCapabilityResolver capabilityResolver;
    private final SystemInfoProvider systemInfoProvider;

    private final AtomicReference<Role> currentRole =
            new AtomicReference<>(Role.UNKNOWN);

    // =========================
    // CONSTRUCTOR
    // =========================

    public RoleManager(
            OSCapabilityResolver capabilityResolver,
            SystemInfoProvider systemInfoProvider
    ) {
        this.capabilityResolver = capabilityResolver;
        this.systemInfoProvider = systemInfoProvider;
    }

    // =========================
    // ROLE RESOLUTION
    // =========================

    public Role resolveRole() {

        // 1. Device eligibility
        if (!isDeviceEligible()) {
            setRole(Role.WORKER);
            return Role.WORKER;
        }

        // 2. Capability scoring
        int score = capabilityResolver.getCapabilityScore();

        // 3. Deterministic election rule (phase 1)
        if (isLowestIpDevice() && score >= 70) {
            setRole(Role.MASTER);
        } else {
            setRole(Role.WORKER);
        }

        Log.d("RoleManager", "Resolved role = " + currentRole.get());

        return currentRole.get();
    }

    // =========================
    // STATE
    // =========================

    public Role getCurrentRole() {
        return currentRole.get();
    }

    public boolean isMaster() {
        return currentRole.get() == Role.MASTER;
    }

    public boolean isWorker() {
        return currentRole.get() == Role.WORKER;
    }

    // =========================
    // RULE ENGINE
    // =========================

    private boolean isDeviceEligible() {
        if (systemInfoProvider.isEmulator()) return false;
        if (systemInfoProvider.isRooted()) return false;
        if (systemInfoProvider.isDebuggableBuild()) return false;

        return true;
    }

    private boolean isLowestIpDevice() {

        String ip = systemInfoProvider.getLocalIp();
        if (ip == null) return false;

        // phase 1 heuristic
        return ip.endsWith(".1") || ip.endsWith(".2");
    }

    private void setRole(Role role) {
        currentRole.set(role);
    }

    // =========================
    // DEBUG
    // =========================

    public void dumpState() {
        Log.d("RoleManager",
                "Role=" + currentRole.get()
                        + ", IP=" + systemInfoProvider.getLocalIp()
                        + ", Score=" + capabilityResolver.getCapabilityScore()
        );
    }
}