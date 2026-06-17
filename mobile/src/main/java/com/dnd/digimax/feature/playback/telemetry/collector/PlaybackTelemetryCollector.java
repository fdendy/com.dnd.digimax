package com.dnd.digimax.feature.playback.telemetry.collector;

import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaybackTelemetryCollector
        implements TelemetryCollector {

    @Override
    public List<TelemetryEvent> collect() {

        List<TelemetryEvent> events =
                new ArrayList<>();

        events.add(
                new TelemetryEvent(
                        "playback",
                        "status",
                        "playing"
                )
        );

        return events;
    }
}