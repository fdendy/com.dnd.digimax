package com.dnd.digimax.feature.playback.pop.provider;

import org.json.JSONObject;
import org.json.JSONException;

import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public class AmgPopProvider
        implements PopProvider {

    @Override
    public String getProviderName() {

        return "amg";
    }

    @Override
    public JSONObject buildPayload(
            PopPayload payload) {

        JSONObject json =
                new JSONObject();

        try {
            json.put(
                    "deviceId",
                    payload.getDeviceId());

            json.put(
                    "contentId",
                    payload.getContentId());

            json.put(
                    "duration",
                    payload.getDuration());

            json.put(
                    "completed",
                    payload.isCompleted());
        } catch (JSONException e) {
            throw new IllegalStateException(
                    "Failed to build AMG POP payload",
                    e);
        }

        return json;
    }
}
