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

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.adapters.WeekRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.utils.DateUtils;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_NEW_CARD_FLAG;

public class CurrentWeekFragment extends Fragment {

    private WeekRecyclerAdapter mWeekRecyclerAdapter;
    private Realm mRealm;

    public static CurrentWeekFragment newInstance() {
        return new CurrentWeekFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        // Создаем и настраиваем адаптер
        RecyclerView recyclerView = view.findViewById(R.id.week_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRealm = Realm.getDefaultInstance();
        Date today = new Date();
        Date weekStartDate = DateUtils.getWeekStartDate(today);
        Date weekEndDate = DateUtils.getWeekEndDate(today);
        RealmResults<Card> cards = mRealm.where(Card.class)
                .between("creationDate", weekStartDate, weekEndDate)
                .findAll();

        mWeekRecyclerAdapter = new WeekRecyclerAdapter(getContext(), cards);

        recyclerView.setAdapter(mWeekRecyclerAdapter);

        // Нажатие на FloatingActionButton создает новую карточку
        FloatingActionButton fab = view.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим к созданию новой карточки
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Intent intent = new Intent(getContext(), CardActivity.class);
                        intent.putExtra(EXTRA_NEW_CARD_FLAG, true);
                        getContext().startActivity(intent);
                    }
                });
            }
        });

        return view;
    }
}
