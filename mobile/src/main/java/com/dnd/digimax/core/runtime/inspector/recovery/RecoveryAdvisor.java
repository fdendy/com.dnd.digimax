package com.dnd.digimax.core.runtime.inspector.recovery;

import com.dnd.digimax.core.runtime.inspector.resource.*;
import com.dnd.digimax.core.runtime.inspector.signal.*;

public class RecoveryAdvisor {

    public RecoveryAction suggest(
            CpuMonitor cpu,
            MemoryMonitor memory,
            StorageMonitor storage,
            ThermalMonitor thermal,
            EnvironmentDriftDetector drift,
            TamperStateChecker tamper
    ) {

        if (cpu == null || memory == null || storage == null ||
                thermal == null || drift == null || tamper == null) {
            return RecoveryAction.RESTART_SERVICE;
        }

        if (!tamper.isTampered()) {
            return RecoveryAction.RESTART_APP;
        }

        if (!memory.isHealthy() && !cpu.isHealthy()) {
            return RecoveryAction.RESTART_SERVICE;
        }

        if (!memory.isHealthy()) {
            return RecoveryAction.REDUCE_LOAD;
        }

        if (!cpu.isHealthy() || !thermal.isHealthy()) {
            return RecoveryAction.RESTART_SERVICE;
        }

        if (!storage.isHealthy()) {
            return RecoveryAction.CLEAN_CACHE;
        }

        if (!drift.isEnvironmentStable()) {
            return RecoveryAction.ENTER_SAFE_MODE;
        }

        return RecoveryAction.NONE;
    }
}