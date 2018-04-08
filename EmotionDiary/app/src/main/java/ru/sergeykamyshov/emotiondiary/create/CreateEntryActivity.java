package ru.sergeykamyshov.emotiondiary.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.Repository;

public class CreateEntryActivity extends AppCompatActivity {

    /**
     * Параметр определяет показывать ли кнопку "Назад"
     */
    public static final boolean SHOW_HOME_AS_UP = true;

    private EditText mSituation;
    private EditText mThoughts;
    private EditText mEmotions;
    private EditText mReaction;
    private EditText mDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        init();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(SHOW_HOME_AS_UP);
        }

        CreateEntryViewModel viewModel = ViewModelProviders.of(this).get(CreateEntryViewModel.class);
        LiveData<Entry> liveData = viewModel.getData();
        liveData.observe(this, new Observer<Entry>() {
            @Override
            public void onChanged(@Nullable Entry entry) {
                mSituation.setText(entry.getSituation());
                if (mSituation.getText() != null) {
                    mSituation.setSelection(mSituation.getText().length());
                }
                mThoughts.setText(entry.getThoughts());
                mEmotions.setText(entry.getEmotion());
                mReaction.setText(entry.getReaction());
                Date date = new Date();
                date.setTime(entry.getDate());
                mDate.setText(date.toString());
            }
        });
    }

    private void init() {
        mSituation = findViewById(R.id.edit_situation);
        mThoughts = findViewById(R.id.edit_thoughts);
        mEmotions = findViewById(R.id.edit_emotions);
        mReaction = findViewById(R.id.edit_reaction);
        mDate = findViewById(R.id.edit_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_action_save:
                // save entry to database
                Entry entry = new Entry();
                entry.setSituation(mSituation.getText().toString());
                entry.setThoughts(mThoughts.getText().toString());
                entry.setEmotion(mEmotions.getText().toString());
                entry.setReaction(mReaction.getText().toString());
                entry.setDate(new Date().getTime());

                Repository repository = Repository.getRepository();
                repository.insert(entry);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
