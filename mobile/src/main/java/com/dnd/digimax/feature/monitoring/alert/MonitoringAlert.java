package com.dnd.digimax.feature.monitoring.alert;

public class MonitoringAlert {

    private final String source;

    private final String message;

    private final AlertSeverity severity;

    private final long timestamp;

    public MonitoringAlert(
            String source,
            String message,
            AlertSeverity severity) {

        this.source =
                source;

        this.message =
                message;

        this.severity =
                severity;

        this.timestamp =
                System.currentTimeMillis();
    }

    public String getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public long getTimestamp() {
        return timestamp;
    }
}