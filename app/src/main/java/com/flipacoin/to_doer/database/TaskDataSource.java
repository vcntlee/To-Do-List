package com.flipacoin.to_doer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.flipacoin.to_doer.data.Task;

import java.util.ArrayList;

public class TaskDataSource {

    private Context mContext;
    private TaskSQLiteHelper mSQLHelper;

    public TaskDataSource(Context context){
        mContext = context;
        mSQLHelper = new TaskSQLiteHelper(context);
    }

    private SQLiteDatabase open() {
        return mSQLHelper.getWritableDatabase();
    }
    private void close(SQLiteDatabase db){
        db.close();
    }

    public void create(Task task){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues value = new ContentValues();
        value.put(TaskSQLiteHelper.COLUMN_NAME, task.getName());
        value.put(TaskSQLiteHelper.COLUMN_DATE, task.getDate());
        value.put(TaskSQLiteHelper.COLUMN_NOTES, task.getNotes());
        value.put(TaskSQLiteHelper.COLUMN_LEVEL, task.getLevel());
        value.put(TaskSQLiteHelper.COLUMN_STATUS, task.getStatus());

        db.insert(TaskSQLiteHelper.TASKS_TABLE, null, value);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);


    }

    public ArrayList<Task> retrieve(){
        SQLiteDatabase db = open();

        Cursor cursor = db.query(TaskSQLiteHelper.TASKS_TABLE, null, null, null, null,
                null, null);
        ArrayList<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                Task task = new Task(getIntFromColName(cursor, BaseColumns._ID),
                        getStringFromColName(cursor, TaskSQLiteHelper.COLUMN_NAME),
                        getStringFromColName(cursor, TaskSQLiteHelper.COLUMN_DATE),
                        getStringFromColName(cursor, TaskSQLiteHelper.COLUMN_NOTES),
                        getIntFromColName(cursor, TaskSQLiteHelper.COLUMN_LEVEL),
                        getIntFromColName(cursor, TaskSQLiteHelper.COLUMN_STATUS));
                tasks.add(task);
            }while(cursor.moveToNext());
        }

        cursor.close();
        close(db);
        return tasks;
    }


    public void update(Task task, String name, String date, String notes, int level,
                       int status){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues value = new ContentValues();
        if (name != null){
            value.put(TaskSQLiteHelper.COLUMN_NAME, name);
        }
        if (date != null){
            value.put(TaskSQLiteHelper.COLUMN_DATE, date);
        }
        if (notes != null){
            value.put(TaskSQLiteHelper.COLUMN_NOTES, notes);
        }
        if (level != -1){
            value.put(TaskSQLiteHelper.COLUMN_LEVEL, level);
        }
        if (status != -1){
            value.put(TaskSQLiteHelper.COLUMN_STATUS, status);
        }

        db.update(TaskSQLiteHelper.TASKS_TABLE, value,
                String.format("%s=%d", BaseColumns._ID, task.getId()),
                null);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);

    }

    public void delete(Task task){
        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(TaskSQLiteHelper.TASKS_TABLE,
                String.format("%s=%d", BaseColumns._ID, task.getId()),
                null);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);


    }

    private int getIntFromColName(Cursor cursor, String colName){
        int colIndex = cursor.getColumnIndex(colName);
        return cursor.getInt(colIndex);
    }

    private String getStringFromColName(Cursor cursor, String colName){
        int colIndex = cursor.getColumnIndex(colName);
        return cursor.getString(colIndex);

    }



}
