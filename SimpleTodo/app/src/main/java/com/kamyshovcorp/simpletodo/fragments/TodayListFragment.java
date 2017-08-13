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
import com.kamyshovcorp.simpletodo.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class TodayListFragment extends Fragment {

    public static TodayListFragment newInstance() {
        return new TodayListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_today, container, false);

        final List<Task> tasks = getTodayTasksFromDb();
        if (tasks.isEmpty()) {
            setEmptyImageBackground((CoordinatorLayout) view);
        } else {
            ListView todayList = (ListView) view.findViewById(R.id.todayListView);
            todayList.setAdapter(new TaskAdapter(getContext(), tasks));

            todayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        FloatingActionButton todayListFab = (FloatingActionButton) view.findViewById(R.id.todayListFab);
        todayListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todayDate = DateUtils.getFormatedDate(new Date());
                Task task = new Task(null, null, todayDate);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, TaskFragment.newInstance(task))
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
            activity.setActionBarTitle(getString(R.string.actionbar_title_today));
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

    private List<Task> getTodayTasksFromDb() {
        TaskStore taskStore = new TaskStore(getContext());
        return taskStore.readTodayTasks();
    }
}
