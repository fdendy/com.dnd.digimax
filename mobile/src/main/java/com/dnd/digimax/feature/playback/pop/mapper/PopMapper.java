package com.dnd.digimax.feature.playback.pop.mapper;

import com.dnd.digimax.feature.playback.pop.ProofOfPlayRecord;
import com.dnd.digimax.feature.playback.pop.uploader.PopPayload;

public final class PopMapper {

    private PopMapper() {
    }

    public static PopPayload toPayload(
            String deviceId,
            ProofOfPlayRecord record) {

        PopPayload payload =
                new PopPayload();

        payload.setDeviceId(deviceId);

        payload.setSessionId(
                record.getSessionId());

        payload.setScheduleId(
                record.getScheduleId());

        payload.setPlaylistId(
                record.getPlaylistId());

        payload.setContentId(
                record.getContentId());

        payload.setPluginType(
                record.getPluginType());

        payload.setStartedAt(
                record.getStartedAt());

        payload.setCompletedAt(
                record.getCompletedAt());

        payload.setDuration(
                record.getDuration());

        payload.setCompleted(
                record.isCompleted());

        return payload;
    }
}