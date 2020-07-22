package com.example.mindmaper.Database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface EStyleDao {

    @Insert
    long insert(EStyle eStyle);
}
