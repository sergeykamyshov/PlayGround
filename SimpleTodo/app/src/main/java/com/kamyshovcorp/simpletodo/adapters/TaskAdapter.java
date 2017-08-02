package com.kamyshovcorp.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kamyshovcorp.simpletodo.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_with_checkbox, parent, false);
        }

        TextView listItemTextView = (TextView) view.findViewById(R.id.listItemTextView);
        listItemTextView.setText(tasks.get(position));

        return view;
    }
}
