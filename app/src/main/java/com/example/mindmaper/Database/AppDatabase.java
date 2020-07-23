package com.example.mindmaper.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {EMap.class,ENode.class,EStyle.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EMapDao eMapDao();
    public abstract ENodeDao eNodeDao();
    public abstract EStyleDao eStyleDao();
}
