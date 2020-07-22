package com.example.mindmaper.Database;


import com.example.mindmaper.Map;
import com.example.mindmaper.Style;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
        (foreignKeys = {@ForeignKey(entity = ENode.class, parentColumns = "id", childColumns = "central_node_id")})
public class EMap {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "central_node_id")
    @Nullable
    public Long centralNodeId;

    public String name;
}




