package com.dnd.digimax.core.runtime.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dnd.digimax.core.runtime.storage.dao.ContentDao;
import com.dnd.digimax.core.runtime.storage.entity.ContentEntity;

@Database(
        entities = {
                ContentEntity.class
        },
        version = 1
)
public abstract class DigimaxDatabase
        extends RoomDatabase {

    public abstract ContentDao contentDao();
}