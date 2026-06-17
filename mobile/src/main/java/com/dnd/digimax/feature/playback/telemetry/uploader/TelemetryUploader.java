package com.dnd.digimax.feature.playback.telemetry.uploader;

import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;

import java.util.List;

public interface TelemetryUploader {

    boolean upload(
            List<TelemetryEvent> events
    );
}