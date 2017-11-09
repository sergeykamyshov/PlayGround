package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.CardRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.database.CardStore;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_INDEX = "cardIndex";

    private CardStore mCardStore = CardStore.getInstance();
    private int mCardIndex;
    private CardRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Получаем данные из вызванного интента
        Intent intent = getIntent();
        mCardIndex = intent.getIntExtra(EXTRA_CARD_INDEX, 0);
        Card card = mCardStore.getCard(mCardIndex);

        // Настраиваем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            TextView cardTitle = findViewById(R.id.txt_card_title);
            cardTitle.setText(card.getTitle());
        }

        final EditText cardTitle = findViewById(R.id.txt_card_title);
        // Делаем заголовок карточки доступным для редактирования при касании
        cardTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardTitle.setFocusable(true);
                cardTitle.setFocusableInTouchMode(true);
                return false;
            }
        });
        // Обновляем данные после изменения заголовка карточки
        cardTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CardStore.changeCardTitle(mCardIndex, s.toString());
            }
        });

        // Устанавливаем адаптер
        RecyclerView recyclerTasks = findViewById(R.id.recycler_tasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardRecyclerAdapter(this, card.getTasks());
        recyclerTasks.setAdapter(mAdapter);

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
                CardStore.removeCard(mCardIndex);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTaskAction(View view) {
        mAdapter.addTask(new Task(Task.DEFAULT_DONE, Task.DEFAULT_DESCRIPTION));
    }
}
