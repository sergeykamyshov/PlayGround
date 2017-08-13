package com.kamyshovcorp.simpletodo.adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
                    Snackbar.make(parent, R.string.snackbar_task_done, Snackbar.LENGTH_LONG).setAction(R.string.snackbar_undo, undoListener).show();

                    if (tasks.isEmpty()) {
                        setEmptyImageBackground();
                    }
                }
            }

            private void setEmptyImageBackground() {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.ic_empty_inbox_background_24dp);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);

                CoordinatorLayout layout = (CoordinatorLayout) parent.getParent();
                layout.addView(imageView);
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
            Snackbar.make(view, R.string.snackbar_task_restored, Snackbar.LENGTH_LONG).show();
        }
    };
}
