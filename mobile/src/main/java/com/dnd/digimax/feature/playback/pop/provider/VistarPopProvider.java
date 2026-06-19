package com.dnd.digimax.feature.playback.pop.provider;

import org.json.JSONObject;
import org.json.JSONException;

import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public class VistarPopProvider
        implements PopProvider {

    @Override
    public String getProviderName() {

        return "vistar";
    }

    @Override
    public JSONObject buildPayload(
            PopPayload payload) {

        JSONObject json =
                new JSONObject();

        try {
            json.put(
                    "screenId",
                    payload.getDeviceId());

            json.put(
                    "assetId",
                    payload.getContentId());

            json.put(
                    "playStart",
                    payload.getStartedAt());

            json.put(
                    "playEnd",
                    payload.getCompletedAt());
        } catch (JSONException e) {
            throw new IllegalStateException(
                    "Failed to build Vistar POP payload",
                    e);
        }

        return json;
    }
}
