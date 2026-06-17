package com.dnd.digimax.feature.playback.pop;

import org.json.JSONArray;
import org.json.JSONException;

public class ProofOfPlayReporter {

    private final ProofOfPlayExporter exporter;

    public ProofOfPlayReporter() {

        exporter =
                new ProofOfPlayExporter();
    }

    public JSONArray buildPayload(
            ProofOfPlayCollector collector) throws JSONException {

        return exporter.export(
                collector.getRecords()
        );
    }
}