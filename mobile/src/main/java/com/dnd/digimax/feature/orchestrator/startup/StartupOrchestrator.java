package com.dnd.digimax.feature.orchestrator.startup;

import com.dnd.digimax.core.security.coordinator.SecurityCoordinator;
import com.dnd.digimax.core.system.SystemInfoProvider;
import com.dnd.digimax.feature.monitoring.engine.MonitoringEngine;

public class StartupOrchestrator {

    private final SecurityCoordinator securityCoordinator;

    private final SystemInfoProvider systemInfoProvider;

    private final MonitoringEngine monitoringEngine;

    public StartupOrchestrator(
            SecurityCoordinator securityCoordinator,
            SystemInfoProvider systemInfoProvider,
            MonitoringEngine monitoringEngine) {

        this.securityCoordinator =
                securityCoordinator;

        this.systemInfoProvider =
                systemInfoProvider;

        this.monitoringEngine =
                monitoringEngine;
    }

    public boolean startup() {

        if (!securityCoordinator
                .checkStartupIntegrity()) {

            return false;
        }

        systemInfoProvider.load();

        monitoringEngine.clear();

        return true;
    }
}