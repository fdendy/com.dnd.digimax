package com.dnd.digimax.feature.orchestrator.monitoring;

import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.engine.MonitoringEngine;
import com.dnd.digimax.feature.orchestrator.recovery.RecoveryOrchestrator;

public class MonitoringOrchestrator {

    private final MonitoringEngine monitoringEngine;

    private final RecoveryOrchestrator recoveryOrchestrator;

    public MonitoringOrchestrator(
            MonitoringEngine monitoringEngine,
            RecoveryOrchestrator recoveryOrchestrator) {

        this.monitoringEngine =
                monitoringEngine;

        this.recoveryOrchestrator =
                recoveryOrchestrator;
    }

    public void evaluatePlayback(
            int errors) {

        monitoringEngine.evaluatePlayback(
                errors
        );

        triggerRecovery();
    }

    public void evaluateRuntime(
            int risk) {

        monitoringEngine.evaluateRuntime(
                risk
        );

        triggerRecovery();
    }

    public void evaluatePop(
            int pendingPop) {

        monitoringEngine.evaluatePop(
                pendingPop
        );

        triggerRecovery();
    }

    public void evaluateTelemetry(
            int queueSize) {

        monitoringEngine.evaluateTelemetry(
                queueSize
        );

        triggerRecovery();
    }

    private void triggerRecovery() {

        if (!monitoringEngine
                .hasCriticalAlert()) {

            return;
        }

        for (MonitoringAlert alert :
                monitoringEngine.getAlerts()) {

            recoveryOrchestrator.recover(
                    alert
            );
        }
    }
}