package com.example.mindmaper.Database;

//public static class Style{
//    public static final String TABLE_NAME = "STYLE";
//    public static final String ID = "Id";
//}

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EStyle {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long color;
}
