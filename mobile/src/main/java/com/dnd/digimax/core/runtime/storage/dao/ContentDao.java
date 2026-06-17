package com.dnd.digimax.core.runtime.storage.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dnd.digimax.core.runtime.storage.entity.ContentEntity;

import java.util.List;

@Dao
public interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContentEntity content);

    @Query("SELECT * FROM contents")
    List<ContentEntity> getAll();
}