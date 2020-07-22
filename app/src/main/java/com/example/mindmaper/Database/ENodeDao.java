package com.example.mindmaper.Database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ENodeDao {

    @Insert
    long insert(ENode eNode);
}
