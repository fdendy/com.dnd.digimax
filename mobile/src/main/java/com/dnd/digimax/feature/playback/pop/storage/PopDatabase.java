package com.dnd.digimax.feature.playback.pop.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                PopEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class PopDatabase
        extends RoomDatabase {

    public abstract PopDao popDao();
}