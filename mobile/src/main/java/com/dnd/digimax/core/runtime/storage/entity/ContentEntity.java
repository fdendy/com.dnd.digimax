package com.dnd.digimax.core.runtime.storage.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contents")
public class ContentEntity {

    @PrimaryKey
    @NonNull
    public String contentId;

    public String type;

    public String source;

    public long duration;
}