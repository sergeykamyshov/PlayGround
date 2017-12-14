package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmList;
import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_ID = "cardId";
    public static final String EXTRA_TASK_ID = "taskId";

    private Realm mRealm;
    private Task mTask;
    private String mCardId;
    private String mTaskId;

    private EditText mTaskTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Настраиваем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        mRealm = Realm.getDefaultInstance();

        mTaskTitle = findViewById(R.id.txt_task_title);

        Intent intent = getIntent();
        mCardId = intent.getStringExtra(EXTRA_CARD_ID);
        mTaskId = intent.getStringExtra(EXTRA_TASK_ID);
        if (mTaskId == null) {
            // Создаем новую задачу
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Card card = realm.where(Card.class).equalTo("id", mCardId).findFirst();
                    mTask = new Task();
                    if (card != null) {
                        card.addTask(mTask);
                    }
                }
            });
        } else {
            // Загружаем данные из задачи
            mTask = mRealm.where(Task.class).equalTo("id", mTaskId).findFirst();
            String taskTitle = mTask != null ? mTask.getTask() : null;
            mTaskTitle.setText(taskTitle);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        final String taskTitle = mTaskTitle.getText().toString();
        if (taskTitle.isEmpty()) {
            // Удаляем пустую задачу
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Card card = realm.where(Card.class).equalTo("id", mCardId).findFirst();
                    RealmList<Task> tasks = card.getTasks();
                    if (!tasks.isEmpty()) {
                        Task task = tasks.get(tasks.size() - 1);
                        task.deleteFromRealm();
                    }
                }
            });
            return;
        }
        // Обновляем задачу
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mTask.setTask(taskTitle);
                mRealm.copyToRealmOrUpdate(mTask);
            }
        });
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

}
