package com.kamyshovcorp.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

    public static final String ARG_TASK_ID = "taskId";
    public static final String ARG_TASK_NAME = "taskName";
    public static final String ARG_TASK_DUE_DATE = "taskDueDate";

    private EditText mTaskNameEditText;
    private EditText mTaskDueDateEditText;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, task.getId());
        args.putString(ARG_TASK_NAME, task.getName());
        args.putString(ARG_TASK_DUE_DATE, task.getDueDate());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTaskNameEditText = (EditText) view.findViewById(R.id.taskNameEditText);
        mTaskDueDateEditText = (EditText) view.findViewById(R.id.taskDateTextEdit);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTaskNameEditText.setText(arguments.getString("taskName"));
            mTaskDueDateEditText.setText(arguments.getString("taskDueDate"));
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
                String taskName = mTaskNameEditText.getText().toString();
                if (!taskName.isEmpty()) {
                    String dateString = mTaskDueDateEditText.getText().toString();

                    Task task = new Task(null, taskName, dateString);
                    TaskStore taskStore = TaskStore.get(getContext());
                    // If was opened for editing and task ID is exists - update the task
                    if (getArguments() != null && getArguments().getString(ARG_TASK_ID) != null) {
                        task.setId(getArguments().getString(ARG_TASK_ID));
                        taskStore.updateTask(task);
                    } else {
                        taskStore.addTask(task);
                    }
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
            activity.setActionBarTitle(getString(R.string.actionbar_title_task));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
