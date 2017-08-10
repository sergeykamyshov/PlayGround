package com.kamyshovcorp.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.kamyshovcorp.simpletodo.MainActivity;
import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.database.TaskStore;
import com.kamyshovcorp.simpletodo.model.Task;

public class TaskFragment extends Fragment {

    private String taskName;
    private String taskDueDate;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();

        Bundle args = new Bundle();
        args.putString("taskName", task.getName());
        args.putString("taskDueDate", task.getDueDate());
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);

        EditText taskNameEditText = (EditText) view.findViewById(R.id.taskNameEditText);
        EditText taskDueDateEditText = (EditText) view.findViewById(R.id.taskDateTextEdit);

        Bundle arguments = getArguments();
        if (arguments != null) {
            taskNameEditText.setText(arguments.getString("taskName"));
            taskDueDateEditText.setText(arguments.getString("taskDueDate"));
        }

        ImageView taskDateImageView = (ImageView) view.findViewById(R.id.taskDateImageView);
        taskDateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fragmentTaskFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = (EditText) view.findViewById(R.id.taskNameEditText);
                String taskName = nameEditText.getText().toString();
                if (!taskName.isEmpty()) {
                    EditText dateEditText = (EditText) view.findViewById(R.id.taskDateTextEdit);
                    String dateString = dateEditText.getText().toString();

                    Task task = new Task(taskName, dateString);
                    TaskStore taskStore = TaskStore.get(getContext());
                    taskStore.addTask(task);
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showUpButton();
        }
    }
}
