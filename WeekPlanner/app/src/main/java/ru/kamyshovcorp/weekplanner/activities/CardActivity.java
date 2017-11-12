package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.CardRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_ID = "cardId";

    private CardRecyclerAdapter mAdapter;
    private Realm mRealm;
    private Card mCard;
    private String mCardId;
    private EditText mCardTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Получаем данные из вызванного интента
        Intent intent = getIntent();
        mCardId = intent.getStringExtra(EXTRA_CARD_ID);

        // Достаем из базы указанную карточку
        mRealm = Realm.getDefaultInstance();
        mCard = mRealm.where(Card.class).equalTo("id", mCardId).findFirst();

        // Настраиваем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            TextView cardTitle = findViewById(R.id.txt_card_title);
            cardTitle.setText(mCard != null ? mCard.getTitle() : "");
        }

        mCardTitle = findViewById(R.id.txt_card_title);
        // Делаем заголовок карточки доступным для редактирования при касании
        mCardTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCardTitle.setFocusable(true);
                mCardTitle.setFocusableInTouchMode(true);
                return false;
            }
        });

        // Устанавливаем адаптер
        RecyclerView recyclerTasks = findViewById(R.id.recycler_tasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardRecyclerAdapter(this, mCard.getTasks());
        recyclerTasks.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Сохраняем заголовок карточки
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (mCardTitle != null) {
                    mCard.setTitle(mCardTitle.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete_card:
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Card> cardsById = realm.where(Card.class).equalTo("id", mCardId).findAll();
                        cardsById.deleteAllFromRealm();
                    }
                });
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTaskAction(View view) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mCard.addTask(new Task());
                mAdapter.notifyItemInserted(mCard.getTasks().size() - 1);
            }
        });
    }
}
