package com.dnd.digimax.feature.playback.pop.provider;

import org.json.JSONObject;
import org.json.JSONException;

import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public class BroadsignPopProvider
        implements PopProvider {

    @Override
    public String getProviderName() {

        return "broadsign";
    }

    @Override
    public JSONObject buildPayload(
            PopPayload payload) {

        JSONObject json =
                new JSONObject();

        try {
            json.put(
                    "displayUnit",
                    payload.getDeviceId());

            json.put(
                    "content",
                    payload.getContentId());

            json.put(
                    "duration",
                    payload.getDuration());
        } catch (JSONException e) {
            throw new IllegalStateException(
                    "Failed to build Broadsign POP payload",
                    e);
        }

        return json;
    }
}
