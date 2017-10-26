package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.database.CardStore;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_INDEX = "cardIndex";

    private CardStore mCardStore = CardStore.getInstance();
    private Card mCard;
    private EditText mCardTitle;
    private LinearLayout mTasksLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        int cardIndex = intent.getIntExtra(EXTRA_CARD_INDEX, 0);
        mCard = mCardStore.getCard(cardIndex);

        initView();

        fillCardTitle();
        fillTasksList();
    }

    private void initView() {
        mCardTitle = (EditText) findViewById(R.id.card_title);
        mTasksLayout = (LinearLayout) findViewById(R.id.tasks_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Task task = new Task();
                mCard.addTask(task);
                createTaskItemLayout(task);
                return true;
            case R.id.action_delete_card:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillCardTitle() {
        mCardTitle.setText(mCard.getTitle());
    }

    private void fillTasksList() {
        for (Task task : mCard.getTasks()) {
            createTaskItemLayout(task);
        }
    }

    private void createTaskItemLayout(Task task) {
        LinearLayout taskLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.task_item, mTasksLayout, false);

        CheckBox doneCheckBox = taskLayout.findViewById(R.id.done_task_check_box);
        doneCheckBox.setChecked(task.isDone());

        EditText descriptionEditText = taskLayout.findViewById(R.id.description_task_edit_text);
        descriptionEditText.setText(task.getDescription());

        mTasksLayout.addView(taskLayout);
    }
}
