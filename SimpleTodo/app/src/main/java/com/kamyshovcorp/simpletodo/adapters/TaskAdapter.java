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
import com.kamyshovcorp.simpletodo.TaskCollection;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> tasks;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_with_checkbox, parent, false);
        }

        TextView listItemTextView = (TextView) view.findViewById(R.id.listItemTextView);
        listItemTextView.setText(tasks.get(position));

        CheckBox listItemCheckBox = (CheckBox) view.findViewById(R.id.listItemCheckBox);
        listItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Delete the task if user checked the item
                    TaskCollection.removeTask(position);
                    // Ask to update the list
                    notifyDataSetChanged();
                    // Unchecked the item
                    buttonView.setChecked(false);
                    // Inform user that task is done, and propose to undo the changes
                    Snackbar.make(parent, "Task Is Done", Snackbar.LENGTH_SHORT).setAction("Undo", undoListener).show();
                }
            }
        });

        return view;
    }

    private View.OnClickListener undoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Undo action clicked", Toast.LENGTH_SHORT).show();
        }
    };
}
