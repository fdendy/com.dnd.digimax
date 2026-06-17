package com.dnd.digimax.feature.playback.pop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProofOfPlayExporter {

    public JSONArray export(
            List<ProofOfPlayRecord> records) throws JSONException {

        JSONArray array =
                new JSONArray();

        for (ProofOfPlayRecord record : records) {

            JSONObject object =
                    new JSONObject();

            object.put(
                    "sessionId",
                    record.getSessionId()
            );

            object.put(
                    "scheduleId",
                    record.getScheduleId()
            );

            object.put(
                    "playlistId",
                    record.getPlaylistId()
            );

            object.put(
                    "contentId",
                    record.getContentId()
            );

            object.put(
                    "startedAt",
                    record.getStartedAt()
            );

            object.put(
                    "completedAt",
                    record.getCompletedAt()
            );

            object.put(
                    "duration",
                    record.getDuration()
            );

            object.put(
                    "completed",
                    record.isCompleted()
            );

            array.put(object);
        }

        return array;
    }
}