package com.dnd.digimax.feature.playback.telemetry.collector;

import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;

import java.util.List;

public interface TelemetryCollector {

    List<TelemetryEvent> collect();
}