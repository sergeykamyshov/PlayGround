package com.kamyshovcorp.simpletodo.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.database.TaskStore;
import com.kamyshovcorp.simpletodo.model.Task;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Task> tasks;
    private Task mRemovedTask;
    private int mRemovedPosition;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_with_checkbox, parent, false);
        }

        CheckBox listItemCheckBox = (CheckBox) convertView.findViewById(R.id.listItemCheckBox);
        listItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Delete the task from databse if user checked the item
                    TaskStore taskStore = new TaskStore(context);
                    taskStore.deleteTask(tasks.get(position));
                    // Delete the task from adapter list, to show that it has changes
                    mRemovedTask = tasks.remove(position);
                    mRemovedPosition = position;
                    // Ask to update the list
                    notifyDataSetChanged();
                    // Unchecked the item
                    buttonView.setChecked(false);
                    // Inform user that task is done, and propose to undo the changes
                    Snackbar.make(parent, "Task Is Done", Snackbar.LENGTH_SHORT).setAction("Undo", undoListener).show();
                }
            }
        });

        TextView listItemTextView = (TextView) convertView.findViewById(R.id.listItemTextView);
        listItemTextView.setText(tasks.get(position).getName());

        TextView dueDate = (TextView) convertView.findViewById(R.id.listItemDueDateTextView);
        dueDate.setText(tasks.get(position).getDueDate());

        return convertView;
    }

    private View.OnClickListener undoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Add deleted task to database
            TaskStore taskStore = new TaskStore(context);
            taskStore.addTask(mRemovedTask);
            // Add deleted task to list
            tasks.add(mRemovedPosition, mRemovedTask);
            // Ask to update the list
            notifyDataSetChanged();
            Snackbar.make(view, "Task Was Restored", Snackbar.LENGTH_SHORT).show();
        }
    };
}
