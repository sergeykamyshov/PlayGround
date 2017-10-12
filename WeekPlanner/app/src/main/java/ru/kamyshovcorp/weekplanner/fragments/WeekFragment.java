package ru.kamyshovcorp.weekplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.WeekRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.model.Category;
import ru.kamyshovcorp.weekplanner.model.Task;

public class WeekFragment extends Fragment {

    public static WeekFragment newInstance() {
        return new WeekFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        RecyclerView recylerView = view.findViewById(R.id.week_recycler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Category> data = generateTestData();

        recylerView.setAdapter(new WeekRecyclerAdapter(getContext(), data));

        return view;
    }

    private List<Category> generateTestData() {
        List<Category> categories = new ArrayList<>();

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(true, "Task 1. Long long long long long long long long long description"));
        tasks.add(new Task(true, "Task 2"));
        tasks.add(new Task(false, "Task 3"));

        categories.add(new Category("Category 1", tasks));
        categories.add(new Category("Category 2", tasks));
        categories.add(new Category("Category 3", tasks));
        categories.add(new Category("Category 4", tasks));
        categories.add(new Category("Category 5", tasks));

        return categories;
    }
}
