package com.kamyshovcorp.simpletodo.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.database.TaskDbHelper;
import com.kamyshovcorp.simpletodo.database.TaskDbSchema;

public class TaskFragment extends Fragment {

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fragmentTaskFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText taskNameEditText = (EditText) view.findViewById(R.id.taskNameEditText);
                if (!taskNameEditText.getText().toString().isEmpty()) {
//                    TaskCollection.addTask(taskNameEditText.getText().toString());
                    addTaskToDb(taskNameEditText.getText().toString());
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void addTaskToDb(String task) {
        Log.i("TaskFragment", "--- Open connection ---");
        TaskDbHelper dbHelper = new TaskDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.i("TaskFragment", "--- Prepare values ---");
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.Cols.COLUMN_NAME_TASK, task);
        Log.i("TaskFragment", "--- Insert data ---");
        db.insert(TaskDbSchema.TABLE_NAME, null, values);
        Log.i("TaskFragment", "--- Close connection ---");
        db.close();
        dbHelper.close();
    }
}
