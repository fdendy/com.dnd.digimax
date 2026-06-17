package com.dnd.digimax.feature.monitoring.reporter;

import com.dnd.digimax.feature.monitoring.alert.MonitoringAlert;

import java.util.List;

public class MonitoringReporter {

    public String buildReport(
            List<MonitoringAlert> alerts) {

        StringBuilder builder =
                new StringBuilder();

        builder.append(
                "DIGIMAX MONITORING REPORT\n"
        );

        builder.append(
                        "Alert Count : "
                ).append(alerts.size())
                .append("\n");

        for (MonitoringAlert alert
                : alerts) {

            builder.append(
                            alert.getSeverity()
                    ).append(" | ")
                    .append(alert.getSource())
                    .append(" | ")
                    .append(alert.getMessage())
                    .append("\n");
        }

        return builder.toString();
    }
}