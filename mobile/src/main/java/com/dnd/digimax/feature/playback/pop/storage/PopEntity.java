package com.dnd.digimax.feature.playback.pop.storage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "proof_of_play")
public class PopEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String sessionId;

    public String scheduleId;

    public String playlistId;

    public String contentId;

    public String pluginType;

    public long startedAt;

    public long completedAt;

    public long duration;

    public boolean completed;

    public boolean uploaded;
}