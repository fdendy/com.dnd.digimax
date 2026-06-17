package com.dnd.digimax.feature.playback.pop.batch;

import org.json.JSONArray;

public class BatchCompressor {

    public byte[] compress(
            JSONArray payload) {

        return payload
                .toString()
                .getBytes();
    }
}