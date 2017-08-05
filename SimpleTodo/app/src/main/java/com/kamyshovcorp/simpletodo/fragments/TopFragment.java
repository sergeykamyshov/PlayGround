package com.kamyshovcorp.simpletodo.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.adapters.TaskAdapter;
import com.kamyshovcorp.simpletodo.database.TaskDbHelper;
import com.kamyshovcorp.simpletodo.database.TaskDbSchema;

import java.util.ArrayList;
import java.util.List;

public class TopFragment extends Fragment {

    private FloatingActionButton mFab;

    public static TopFragment newInstance() {
        return new TopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

//        List<String> tasks = TaskCollection.getTasks();
        List<String> tasks = readTasksFromDb();

        if (tasks.isEmpty()) {
            setEmptyImageBackground((CoordinatorLayout) view);
        } else {
            ListView todoList = (ListView) view.findViewById(R.id.inboxList);
            todoList.setAdapter(new TaskAdapter(getContext(), (ArrayList<String>) tasks));
        }

        mFab = (FloatingActionButton) view.findViewById(R.id.fragmentTopFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, TaskFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private List<String> readTasksFromDb() {
        ArrayList<String> dbTasks = new ArrayList<>();
        Log.i("TopFragment", "--- Open connection ---");
        SQLiteOpenHelper dbHelper = new TaskDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.i("TopFragment", "--- Execute query ---");
        Cursor cursor = db.query(TaskDbSchema.TABLE_NAME, null, null, null, null, null, null);
        Log.i("TopFragment", "--- Read result ---");
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int taskIndex = cursor.getColumnIndex("task");
            do {
                String idFromDb = cursor.getString(idIndex);
                String taskFromDb = cursor.getString(taskIndex);
                Log.i("TaskDbHelper", "id: "  + idFromDb + ", task: " + taskFromDb);
                dbTasks.add(taskFromDb);
            } while (cursor.moveToNext());
        }
        Log.i("TopFragment", "--- Close connection ---");
        cursor.close();
        db.close();
        dbHelper.close();

        return dbTasks;
    }

    private void setEmptyImageBackground(CoordinatorLayout view) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_empty_inbox_background_24dp);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);

        view.addView(imageView);
    }
}
