package ru.kamyshovcorp.weekplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardAdapter extends BaseAdapter {

    private Context mContext;
    private List<Task> mTasks;
    private LayoutInflater mLayoutInflater;

    public CardAdapter(Context context, List<Task> tasks) {
        mContext = context;
        mTasks = tasks;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.task_item, parent, false);
        }

        Task task = mTasks.get(position);
        EditText descriptionTask = view.findViewById(R.id.description_task_edit_text);
        descriptionTask.setText(task.getDescription());
        CheckBox doneTask = view.findViewById(R.id.done_task_check_box);
        doneTask.setChecked(task.isDone());

        return view;
    }
}
