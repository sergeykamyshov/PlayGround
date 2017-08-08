package com.kamyshovcorp.simpletodo.database;

import android.provider.BaseColumns;

public class TaskDbSchema implements BaseColumns {

    public static final String TABLE_NAME = "task";

    public static final class Cols {
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_DUE_DATE = "due_date";
    }


}
