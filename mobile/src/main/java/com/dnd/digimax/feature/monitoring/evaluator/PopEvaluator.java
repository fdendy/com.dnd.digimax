package com.dnd.digimax.feature.monitoring.evaluator;

import com.dnd.digimax.feature.monitoring.alert.AlertSeverity;
import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;
import com.dnd.digimax.feature.monitoring.policy.MonitoringPolicy;

public class PopEvaluator {

    public MonitoringAlert evaluate(
            int pendingPop,
            MonitoringPolicy policy) {

        if (pendingPop >
                policy.getMaxPendingPop()) {

            return new MonitoringAlert(
                    "pop",
                    "Pending POP exceeded threshold",
                    AlertSeverity.WARNING
            );
        }

        return null;
    }
}