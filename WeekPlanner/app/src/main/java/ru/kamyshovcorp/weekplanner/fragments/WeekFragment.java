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

        List<String> data = new ArrayList<>();
        data.add("Category example 1");
        data.add("Category example 2");
        data.add("Category example 3");
        data.add("Category example 4");
        data.add("Category example 5");
        data.add("Category example 6");
        data.add("Category example 7");
        data.add("Category example 8");
        data.add("Category example 9");
        data.add("Category example 10");

        recylerView.setAdapter(new WeekRecyclerAdapter(data));

        return view;
    }
}
