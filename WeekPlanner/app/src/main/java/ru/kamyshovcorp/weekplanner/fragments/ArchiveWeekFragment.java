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

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_CARD_ID;

public class ArchiveWeekFragment extends Fragment {

    private WeekRecyclerAdapter mWeekRecyclerAdapter;
    private Realm mRealm;

    public static ArchiveWeekFragment newInstance() {
        return new ArchiveWeekFragment();
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
        Date previousWeekStartDate = DateUtils.getPreviousWeekStartDate(today);
        final Date previousWeekEndDate = DateUtils.getPreviousWeekEndDate(today);
        RealmResults<Card> cards = mRealm.where(Card.class)
                .between("creationDate", previousWeekStartDate, previousWeekEndDate)
                .findAll();


        mWeekRecyclerAdapter = new WeekRecyclerAdapter(getContext(), cards);

        recyclerView.setAdapter(mWeekRecyclerAdapter);

        // Нажатие на FloatingActionButton создает новую карточку в архиве на выбранной неделе
        FloatingActionButton fab = view.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Card card = new Card();
                        String cardId = card.getId();
                        // При создании карточки в архиве, указываем дату выбранной недели
                        card.setCreationDate(previousWeekEndDate);

                        realm.insertOrUpdate(card);

                        Intent intent = new Intent(getContext(), CardActivity.class);
                        intent.putExtra(EXTRA_CARD_ID, cardId);
                        getContext().startActivity(intent);
                    }
                });
            }
        });

        return view;
    }
}
