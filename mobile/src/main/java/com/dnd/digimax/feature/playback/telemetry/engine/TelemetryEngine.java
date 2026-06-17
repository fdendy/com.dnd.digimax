package com.dnd.digimax.feature.playback.telemetry.engine;

import com.dnd.digimax.feature.playback.telemetry.collector.*;
import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;
import com.dnd.digimax.feature.playback.telemetry.reporter.TelemetryReporter;
import com.dnd.digimax.feature.playback.telemetry.storage.TelemetryStorage;
import com.dnd.digimax.feature.playback.telemetry.uploader.TelemetryUploader;

import java.util.ArrayList;
import java.util.List;

public class TelemetryEngine {

    private final List<TelemetryCollector>
            collectors =
            new ArrayList<>();

    private final TelemetryStorage storage;

    private final TelemetryReporter reporter;

    private final TelemetryUploader uploader;

    public TelemetryEngine(
            TelemetryUploader uploader) {

        this.uploader =
                uploader;

        storage =
                new TelemetryStorage();

        reporter =
                new TelemetryReporter();

        collectors.add(
                new RuntimeTelemetryCollector()
        );

        collectors.add(
                new PlaybackTelemetryCollector()
        );

        collectors.add(
                new PopTelemetryCollector()
        );

        collectors.add(
                new SecurityTelemetryCollector()
        );
    }

    public void collect() {

        for (TelemetryCollector collector
                : collectors) {

            List<TelemetryEvent> events =
                    collector.collect();

            for (TelemetryEvent event
                    : events) {

                storage.save(
                        event
                );
            }
        }
    }

    public boolean upload() {

        List<TelemetryEvent> events =
                storage.load();

        boolean success =
                uploader.upload(
                        events
                );

        if (success) {

            storage.clear();
        }

        return success;
    }

    public String report() {

        return reporter.buildReport(
                storage.load()
        );
    }

    public int getPendingCount() {

        return storage.size();
    }
}