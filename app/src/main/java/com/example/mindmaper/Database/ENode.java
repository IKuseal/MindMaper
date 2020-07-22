package com.example.mindmaper.Database;

import com.example.mindmaper.Map;
import com.example.mindmaper.Style;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = EMap.class, parentColumns = "id", childColumns = "map_id"),
                       @ForeignKey(entity = ENode.class, parentColumns = "id", childColumns = "parent_id"),
                       @ForeignKey(entity = EStyle.class, parentColumns = "id", childColumns = "style_id")})
public class ENode {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @Nullable
    @ColumnInfo(name = "parent_id")
    public Long parentId;

    @ColumnInfo(name = "map_id")
    public long mapId;

    @ColumnInfo(name = "style_id")
    public long styleId;

    public int position;

    @ColumnInfo(name = "main_text")
    public String mainText;

    @ColumnInfo(name = "attached_text")
    public String attachedText;

}
