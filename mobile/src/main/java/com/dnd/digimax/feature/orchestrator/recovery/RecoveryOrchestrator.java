package com.dnd.digimax.feature.orchestrator.recovery;

import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.recovery.engine.RecoveryEngine;
import com.dnd.digimax.feature.recovery.plan.RecoveryPlan;

public class RecoveryOrchestrator {

    private final RecoveryEngine recoveryEngine;

    public RecoveryOrchestrator(
            RecoveryEngine recoveryEngine) {

        this.recoveryEngine =
                recoveryEngine;
    }

    public boolean recover(
            MonitoringAlert alert) {

        RecoveryPlan plan =
                recoveryEngine.createPlan(
                        alert
                );

        return recoveryEngine.execute(
                plan
        );
    }
}