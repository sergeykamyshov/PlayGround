package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.adapters.CardRecyclerAdapter;
import ru.kamyshovcorp.weekplanner.database.CardStore;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_INDEX = "cardIndex";

    private CardStore mCardStore = CardStore.getInstance();
    private Card mCard;
    private CardRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        int cardIndex = intent.getIntExtra(EXTRA_CARD_INDEX, 0);
        mCard = mCardStore.getCard(cardIndex);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            TextView cardTitle = findViewById(R.id.txt_card_title);
            cardTitle.setText(mCard.getTitle());
        }

        RecyclerView recyclerTasks = findViewById(R.id.recycler_tasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new CardRecyclerAdapter(this, mCard.getTasks());
        recyclerTasks.setAdapter(mRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTaskAction(View view) {
        mCard.addTask(new Task(Task.DEFAULT_DONE, Task.DEFAULT_DESCRIPTION));
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}
