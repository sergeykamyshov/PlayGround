package com.kamyshovcorp.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.kamyshovcorp.simpletodo.R;
import com.kamyshovcorp.simpletodo.TaskCollection;

import java.util.List;

public class TopFragment extends Fragment {

    private FloatingActionButton mFab;

    public static TopFragment newInstance() {
        return new TopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        List<String> tasks = TaskCollection.getTasks();
        if (tasks.isEmpty()) {
            setEmptyImageBackground((CoordinatorLayout) view);
        } else {
            ListView todoList = (ListView) view.findViewById(R.id.inboxList);
            todoList.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tasks.toArray()));
        }

        mFab = (FloatingActionButton) view.findViewById(R.id.fragmentTopFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, TaskFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void setEmptyImageBackground(CoordinatorLayout view) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_empty_inbox_background_24dp);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);

        view.addView(imageView);
    }
}
