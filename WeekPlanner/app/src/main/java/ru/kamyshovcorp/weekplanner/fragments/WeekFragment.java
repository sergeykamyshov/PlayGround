package ru.kamyshovcorp.weekplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.WeekRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.database.CardStore;
import ru.kamyshovcorp.weekplanner.model.Card;

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

        List<Card> data = CardStore.getInstance().getCards();

        recylerView.setAdapter(new WeekRecyclerAdapter(getContext(), data));

        return view;
    }
}
