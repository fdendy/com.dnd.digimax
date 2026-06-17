package com.dnd.digimax.feature.monitoring.evaluator;

import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.policy.MonitoringPolicy;

public class TelemetryEvaluator {

    public MonitoringAlert evaluate(
            int queueSize,
            MonitoringPolicy policy) {

        if (queueSize >
                policy.getMaxTelemetryQueue()) {

            return new MonitoringAlert(
                    "telemetry",
                    "Telemetry queue exceeded threshold",
                    AlertSeverity.WARNING
            );
        }

        return null;
    }
}