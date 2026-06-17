package com.dnd.digimax.feature.playback.pop.provider;

import org.json.JSONObject;
import org.json.JSONException;

import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public class InternalCmsPopProvider
        implements PopProvider {

    @Override
    public String getProviderName() {

        return "internal";
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
                    "startedAt",
                    payload.getStartedAt());

            json.put(
                    "completedAt",
                    payload.getCompletedAt());

            json.put(
                    "duration",
                    payload.getDuration());
        } catch (JSONException e) {
            throw new IllegalStateException(
                    "Failed to build internal CMS POP payload",
                    e);
        }

        return json;
    }
}
