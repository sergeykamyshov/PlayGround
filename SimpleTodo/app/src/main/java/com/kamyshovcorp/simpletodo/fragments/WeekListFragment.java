package com.kamyshovcorp.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.kamyshovcorp.simpletodo.MainActivity;
import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.adapters.TaskAdapter;
import com.kamyshovcorp.simpletodo.database.TaskStore;
import com.kamyshovcorp.simpletodo.model.Task;

import java.util.List;

public class WeekListFragment extends Fragment {

    public static WeekListFragment newInstance() {
        return new WeekListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_week, container, false);

        final List<Task> tasks = getWeekTasksFromDb();
        if (tasks.isEmpty()) {
            setEmptyImageBackground((CoordinatorLayout) view);
        } else {
            ListView weekList = (ListView) view.findViewById(R.id.weekListView);
            weekList.setAdapter(new TaskAdapter(getContext(), tasks));

            weekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, TaskFragment.newInstance(tasks.get(position)))
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        FloatingActionButton weekListFab = (FloatingActionButton) view.findViewById(R.id.weekListFab);
        weekListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, TaskFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showDrawerButton();
            activity.setActionBarTitle(getString(R.string.actionbar_title_next_week));
        }
    }

    private void setEmptyImageBackground(CoordinatorLayout view) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_empty_inbox_background_24dp);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);

        view.addView(imageView);
    }

    private List<Task> getWeekTasksFromDb() {
        TaskStore taskStore = new TaskStore(getContext());
        return taskStore.readWeekTasks();
    }
}
