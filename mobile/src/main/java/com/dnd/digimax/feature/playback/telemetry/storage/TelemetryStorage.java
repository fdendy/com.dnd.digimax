package com.dnd.digimax.feature.playback.telemetry.storage;

import com.dnd.digimax.feature.playback.telemetry.model.TelemetryEvent;

import java.util.ArrayList;
import java.util.List;

public class TelemetryStorage {

    private final List<TelemetryEvent>
            events =
            new ArrayList<>();

    public synchronized void save(
            TelemetryEvent event) {

        events.add(event);
    }

    public synchronized List<TelemetryEvent> load() {

        return new ArrayList<>(events);
    }

    public synchronized void clear() {

        events.clear();
    }

    public synchronized int size() {

        return events.size();
    }
}