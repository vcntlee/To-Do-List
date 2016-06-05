package com.flipacoin.to_doer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TaskSQLiteHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "tasks.db";
    public static final int DB_VERSION = 1;

    public static final String TASKS_TABLE = "TASKS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_NOTES = "NOTES";
    public static final String COLUMN_LEVEL = "LEVEL";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String CREATE_TASKS_TABLE = "CREATE TABLE " + TASKS_TABLE +
            "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_DATE + " TEXT," +
            COLUMN_NOTES + " TEXT," +
            COLUMN_LEVEL + " INTEGER," +
            COLUMN_STATUS + " INTEGER)";

    public TaskSQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
