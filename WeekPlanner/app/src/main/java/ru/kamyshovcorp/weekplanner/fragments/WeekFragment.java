package ru.kamyshovcorp.weekplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.adapters.WeekRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.database.CardStore;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_CARD_INDEX;

public class WeekFragment extends Fragment {

    private WeekRecyclerAdapter mWeekRecyclerAdapter;

    public static WeekFragment newInstance() {
        return new WeekFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        // Создаем и настраиваем адаптер
        RecyclerView recylerView = view.findViewById(R.id.week_recycler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Card> data = CardStore.getInstance().getCards();
        mWeekRecyclerAdapter = new WeekRecyclerAdapter(getContext(), data);
        recylerView.setAdapter(mWeekRecyclerAdapter);

        // Настраиваем экшин для FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardStore.addCard(new Card("", new ArrayList<Task>()));

                Intent intent = new Intent(getContext(), CardActivity.class);
                intent.putExtra(EXTRA_CARD_INDEX, CardStore.getCards().size() - 1);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWeekRecyclerAdapter.notifyDataSetChanged();
    }
}
