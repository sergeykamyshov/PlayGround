package com.kamyshovcorp.simpletodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskReader";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TaskDbSchema.TABLE_NAME + " (" +
            TaskDbSchema._ID + " INTEGER PRIMARY KEY, " +
            TaskDbSchema.Cols.COLUMN_TASK + " TEXT," +
            TaskDbSchema.Cols.COLUMN_DUE_DATE + " TEXT" +
            ")";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskDbSchema.TABLE_NAME);
        db.execSQL(SQL_CREATE_TABLE);
//        db.execSQL("ALTER TABLE " + TaskDbSchema.TABLE_NAME + " ADD COLUMN " + TaskDbSchema.Cols.COLUMN_DUE_DATE + " TEXT");
    }
}
