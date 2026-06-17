package com.dnd.digimax.feature.playback.pop.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PopDao {

    @Insert
    long insert(
            PopEntity entity
    );

    @Delete
    void delete(
            PopEntity entity
    );

    @Query(
            "SELECT * FROM proof_of_play " +
                    "WHERE uploaded = 0"
    )
    List<PopEntity> getPending();

    @Query(
            "UPDATE proof_of_play " +
                    "SET uploaded = 1 " +
                    "WHERE id = :id"
    )
    void markUploaded(
            long id
    );
}