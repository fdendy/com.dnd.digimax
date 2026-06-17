package com.dnd.digimax.feature.playback.telemetry.reporter;

import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;

import java.util.List;

public class TelemetryReporter {

    public String buildReport(
            List<TelemetryEvent> events) {

        StringBuilder builder =
                new StringBuilder();

        builder.append(
                "Telemetry Report\n"
        );

        builder.append(
                "Total Events : "
        ).append(events.size());

        return builder.toString();
    }
}