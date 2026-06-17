package com.dnd.digimax.feature.monitoring.evaluator;

import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.policy.MonitoringPolicy;

public class RuntimeEvaluator {

    public MonitoringAlert evaluate(
            int runtimeRisk,
            MonitoringPolicy policy) {

        if (runtimeRisk >
                policy.getMaxRuntimeRisk()) {

            return new MonitoringAlert(
                    "runtime",
                    "Runtime risk exceeded threshold",
                    AlertSeverity.CRITICAL
            );
        }

        return null;
    }
}