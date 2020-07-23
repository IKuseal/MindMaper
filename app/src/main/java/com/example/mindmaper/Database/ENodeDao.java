package com.example.mindmaper.Database;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ENodeDao {

    @Insert
    long insert(ENode eNode);

    @Query("SELECT * FROM enode WHERE parent_id = :parentId")
    public List<ENode> getENodesWithParentId(long parentId);

    @Query("SELECT * FROM enode WHERE id = :id")
    public ENode getENodeWithId(long id);

    @Query("UPDATE enode SET position = :position WHERE id = :id")
    public void updatePosition(long id,int position);

}
