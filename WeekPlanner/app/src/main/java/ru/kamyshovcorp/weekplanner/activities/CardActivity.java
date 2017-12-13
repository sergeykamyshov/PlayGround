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
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmList;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.CardRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_ID = "cardId";
    public static final String EXTRA_NEW_CARD_FLAG = "newCardFlag";

    private CardRecyclerAdapter mAdapter;
    private Realm mRealm;
    private Card mCard;
    private String mCardId;
    private EditText mCardTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Получаем данные из вызываемого интента
        Intent intent = getIntent();
        mCardId = intent.getStringExtra(EXTRA_CARD_ID);

        // Загружаем выбранную карточку
        mRealm = Realm.getDefaultInstance();
        mCard = mRealm.where(Card.class).equalTo("id", mCardId).findFirst();

        // Настраиваем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mCard != null ? mCard.getTitle() : "");
        }

        // Устанавливаем адаптер
        RecyclerView recyclerTasks = findViewById(R.id.recycler_tasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardRecyclerAdapter(this, mCard.getTasks());
        recyclerTasks.setAdapter(mAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Удаляем пустую карточку
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if ((mCard.getTitle() == null || mCard.getTitle().isEmpty()) && mCard.getTasks().isEmpty()) {
                    mCard.deleteFromRealm();
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
                        Card card = realm.where(Card.class).equalTo("id", mCardId).findFirst();
                        if (card != null) {
                            RealmList<Task> tasks = card.getTasks();
                            // Удаляем все задачи, которые были внутри карточки
                            for (int i = tasks.size() - 1; tasks.size() > 0; i--) {
                                Task task = tasks.get(i);
                                if (task != null) {
                                    task.deleteFromRealm();
                                }
                            }
                            card.deleteFromRealm();
                        }
                    }
                });
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTaskAction(View view) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TaskActivity.EXTRA_CARD_ID, mCardId);
        startActivity(intent);
    }
}
