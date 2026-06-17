package com.dnd.digimax.feature.playback.pop.provider;

import org.json.JSONObject;

import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public interface PopProvider {

    String getProviderName();

    JSONObject buildPayload(
            PopPayload payload);
}