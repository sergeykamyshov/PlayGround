package com.kamyshovcorp.simpletodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kamyshovcorp.simpletodo.model.Task;
import com.kamyshovcorp.simpletodo.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskStore {

    private static TaskStore sTaskStore;
    private SQLiteDatabase mDatabase;

    public TaskStore(Context context) {
        Context applicationContext = context.getApplicationContext();
        TaskDbHelper dbHelper = new TaskDbHelper(applicationContext);
        mDatabase = dbHelper.getWritableDatabase();
    }

    public static TaskStore get(Context context) {
        if (sTaskStore == null) {
            return new TaskStore(context);
        }
        return sTaskStore;
    }

    public List<Task> readAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = mDatabase.query(TaskDbSchema.TABLE_NAME, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("_id");
                int nameIndex = cursor.getColumnIndex("task");
                int dueDateIndex = cursor.getColumnIndex("due_date");
                do {
                    String idFromDb = cursor.getString(idIndex);
                    String nameFromDb = cursor.getString(nameIndex);
                    String dueDateFromDb = cursor.getString(dueDateIndex);
                    tasks.add(new Task(idFromDb, nameFromDb, dueDateFromDb));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public List<Task> readTodayTasks() {
        List<Task> tasks = new ArrayList<>();
        String todayDate = DateUtils.getFormatedDate(new Date());
        Cursor cursor = mDatabase.query(TaskDbSchema.TABLE_NAME, null, TaskDbSchema.Cols.COLUMN_DUE_DATE + " = ?", new String[]{todayDate}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("_id");
                int nameIndex = cursor.getColumnIndex("task");
                int dueDateIndex = cursor.getColumnIndex("due_date");
                do {
                    String idFromDb = cursor.getString(idIndex);
                    String nameFromDb = cursor.getString(nameIndex);
                    String dueDateFromDb = cursor.getString(dueDateIndex);
                    tasks.add(new Task(idFromDb, nameFromDb, dueDateFromDb));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public List<Task> readWeekTasks() {
        List<Task> tasks = new ArrayList<>();

        Date today = new Date();
        String todayDate = DateUtils.getFormatedDate(today);
        String afterWeekDate = DateUtils.getFormatedDateAfterWeek(today);

        String selection = TaskDbSchema.Cols.COLUMN_DUE_DATE + " BETWEEN ? AND ?";
        String[] selectionArgs = {todayDate, afterWeekDate};
        Cursor cursor = mDatabase.query(TaskDbSchema.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("_id");
                int nameIndex = cursor.getColumnIndex("task");
                int dueDateIndex = cursor.getColumnIndex("due_date");
                do {
                    String idFromDb = cursor.getString(idIndex);
                    String nameFromDb = cursor.getString(nameIndex);
                    String dueDateFromDb = cursor.getString(dueDateIndex);
                    tasks.add(new Task(idFromDb, nameFromDb, dueDateFromDb));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        if (task.getId() != null) {
            values.put(TaskDbSchema._ID, task.getId());
        }
        values.put(TaskDbSchema.Cols.COLUMN_TASK, task.getName());
        values.put(TaskDbSchema.Cols.COLUMN_DUE_DATE, task.getDueDate());
        mDatabase.insert(TaskDbSchema.TABLE_NAME, null, values);
    }

    public void updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.Cols.COLUMN_TASK, task.getName());
        values.put(TaskDbSchema.Cols.COLUMN_DUE_DATE, task.getDueDate());
        mDatabase.update(TaskDbSchema.TABLE_NAME, values, TaskDbSchema._ID + " = ?", new String[]{task.getId()});
    }

    public void deleteTask(Task task) {
        mDatabase.delete(TaskDbSchema.TABLE_NAME, TaskDbSchema._ID + " = ?", new String[]{task.getId()});
    }
}
