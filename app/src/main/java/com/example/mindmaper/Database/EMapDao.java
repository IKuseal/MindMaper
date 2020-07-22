package com.example.mindmaper.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EMapDao{
    @Query("SELECT * FROM emap")
    List<EMap> getAll();

    @Query(" UPDATE emap SET central_node_id = :centralNodeId WHERE id = :mapId ")
    public void updateCentralNodeId(long mapId, Long centralNodeId);

    @Insert
    long insert(EMap eMap);

}
