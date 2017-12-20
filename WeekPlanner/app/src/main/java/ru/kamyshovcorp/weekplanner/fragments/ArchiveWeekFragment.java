package ru.kamyshovcorp.weekplanner.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.activities.CardActivity;
import ru.kamyshovcorp.weekplanner.adapters.WeekRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.utils.DateUtils;
import ru.kamyshovcorp.weekplanner.views.EmptyRecyclerView;

import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_ARCHIVE_FLAG;
import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_NEW_CARD_FLAG;
import static ru.kamyshovcorp.weekplanner.activities.CardActivity.EXTRA_WEEK_END_DATE;

public class ArchiveWeekFragment extends Fragment {

    private WeekRecyclerAdapter mWeekRecyclerAdapter;
    private Realm mRealm;
    private Date mWeekStartDate;
    private Date mWeekEndDate;

    public static ArchiveWeekFragment newInstance() {
        return new ArchiveWeekFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        // Создаем и настраиваем адаптер
        EmptyRecyclerView recyclerView = view.findViewById(R.id.week_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setEmptyView(view.findViewById(R.id.layout_empty_card_list));

        mRealm = Realm.getDefaultInstance();
        // Показываем прошлую неделю по умолчанию
        Date today = new Date();
        mWeekStartDate = DateUtils.getPreviousWeekStartDate(today);
        mWeekEndDate = DateUtils.getPreviousWeekEndDate(today);
        RealmResults<Card> cards = mRealm.where(Card.class)
                .between("creationDate", mWeekStartDate, mWeekEndDate)
                .findAll();

        mWeekRecyclerAdapter = new WeekRecyclerAdapter(getContext(), cards);
        recyclerView.setAdapter(mWeekRecyclerAdapter);

        // Нажатие на FloatingActionButton создает новую карточку в архиве на выбранной неделе
        FloatingActionButton fab = view.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим к созданию новой карточки на выбранной неделе архива
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Intent intent = new Intent(getContext(), CardActivity.class);
                        intent.putExtra(EXTRA_NEW_CARD_FLAG, true);
                        intent.putExtra(EXTRA_ARCHIVE_FLAG, true);
                        intent.putExtra(EXTRA_WEEK_END_DATE, mWeekEndDate);
                        getContext().startActivity(intent);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_archive, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_week:
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setCallBack(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                        mWeekStartDate = DateUtils.getWeekStartDate(calendar.getTime());
                        mWeekEndDate = DateUtils.getWeekEndDate(calendar.getTime());
                        RealmResults<Card> cards = mRealm.where(Card.class)
                                .between("creationDate", mWeekStartDate, mWeekEndDate)
                                .findAll();
                        mWeekRecyclerAdapter.setCards(cards);
                    }
                });
                datePicker.show(getFragmentManager(), "datePicker");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
