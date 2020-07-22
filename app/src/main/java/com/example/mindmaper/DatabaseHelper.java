package com.example.mindmaper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MindMaperDatabase.db"; // название бд
    private static final int SCHEMA = 0; // версия базы данных

    public static class Map{
        public static final String TABLE_NAME = "MAP";
        public static final String ID = "Id";
        public static final String CENTRAL_NODE_ID = "CentralNodeId";
        public static final String NAME = "Name";
    }
    public static class Node{
        public static final String TABLE_NAME = "NODE";
        public static final String ID = "Id";
        public static final String PARENT_ID = "ParentId";
        public static final String MAP_ID = "MapId";
        public static final String STYLE_ID = "StyleId";
        public static final String POSITION = "Position";
        public static final String MAIN_TEXT = "MainText";
        public static final String ATTACHED_TEXT = "AttachedText";
    }

    public static class Style{
        public static final String TABLE_NAME = "STYLE";
        public static final String ID = "Id";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Map.TABLE_NAME + "(" +
                Map.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Map.CENTRAL_NODE_ID + " INTEGER NOT NULL,"+
                Map.NAME + " TEXT NOT NULL,"+
                " FOREIGN KEY ("+Map.CENTRAL_NODE_ID+") REFERENCES " +
                Node.TABLE_NAME + "(" + Node.ID +
                "));");


        db.execSQL("CREATE TABLE " + Node.TABLE_NAME + "(" +
                Node.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Node.PARENT_ID + " INTEGER NOT NULL,"+
                Node.MAP_ID + " INTEGER NOT NULL,"+
                Node.STYLE_ID + " INTEGER NOT NULL,"+
                Node.POSITION + " INTEGER NOT NULL,"+
                Node.MAIN_TEXT + " TEXT NOT NULL,"+
                Node.ATTACHED_TEXT + " TEXT NOT NULL,"+

                " FOREIGN KEY ("+Node.PARENT_ID+") REFERENCES " +
                Node.TABLE_NAME + "("+Node.ID+"),"+

                " FOREIGN KEY ("+Node.MAP_ID+") REFERENCES " +
                Node.TABLE_NAME + "("+Map.ID+"),"+

                " FOREIGN KEY ("+Node.STYLE_ID+") REFERENCES " +
                Node.TABLE_NAME + "("+Style.ID+")"+ ");");

        db.execSQL("CREATE TABLE " + Map.TABLE_NAME + "(" +
                Map.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Map.CENTRAL_NODE_ID + " INTEGER NOT NULL,"+
                Map.NAME + " TEXT NOT NULL,"+
                " FOREIGN KEY ("+Map.CENTRAL_NODE_ID+") REFERENCES " +
                Node.TABLE_NAME + "(" + Node.ID +
                "));");

        db.execSQL("CREATE TABLE " + Style.TABLE_NAME + "(" +
                Style.ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +");");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Map.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Node.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Style.TABLE_NAME);


        onCreate(db);
    }
}
