package com.dnd.digimax.feature.playback.telemetry.model;

public class TelemetryEvent {

    private String category;

    private String metric;

    private String value;

    private long timestamp;

    public TelemetryEvent() {

        timestamp =
                System.currentTimeMillis();
    }

    public TelemetryEvent(
            String category,
            String metric,
            String value) {

        this.category =
                category;

        this.metric =
                metric;

        this.value =
                value;

        this.timestamp =
                System.currentTimeMillis();
    }

    public String getCategory() {
        return category;
    }

    public String getMetric() {
        return metric;
    }

    public String getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}