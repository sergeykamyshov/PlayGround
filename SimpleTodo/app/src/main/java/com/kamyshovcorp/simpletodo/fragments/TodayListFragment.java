package com.kamyshovcorp.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kamyshovcorp.simpletodo.MainActivity;
import com.kamyshovcorp.simpletodo.R;

public class TodayListFragment extends Fragment {

    public static TodayListFragment newInstance() {
        return new TodayListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_today, container, false);
        setEmptyImageBackground((CoordinatorLayout) view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideUpButton();
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
}
