package ru.sergeykamyshov.emotiondiary.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykamyshov.emotiondiary.R;
import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.Repository;

public class CreateEntryActivity extends AppCompatActivity {

    /**
     * Параметр определяет показывать ли кнопку "Назад"
     */
    public static final boolean SHOW_HOME_AS_UP = true;
    public static final String EXTRA_ENTRY_ID = "entryId";
    public static final long DEFAULT_ENTRY_ID = 0L;

    private Repository mRepository = Repository.getRepository();
    private long mEntryId;
    private LiveData<Entry> mLiveData;

    private EditText mSituation;
    private EditText mThoughts;
    private EditText mEmotions;
    private EditText mReaction;
    private EditText mDate;
    private Button mSaveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        init();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(SHOW_HOME_AS_UP);
        }

        Intent intent = getIntent();
        if (intent != null && intent.getLongExtra(EXTRA_ENTRY_ID, DEFAULT_ENTRY_ID) != DEFAULT_ENTRY_ID) {
            mEntryId = intent.getLongExtra(EXTRA_ENTRY_ID, DEFAULT_ENTRY_ID);
            CreateEntryViewModel viewModel = ViewModelProviders.of(this).get(CreateEntryViewModel.class);
            mLiveData = viewModel.getData(mEntryId);
            mLiveData.observe(this, new Observer<Entry>() {
                @Override
                public void onChanged(@Nullable Entry entry) {
                    if (entry != null) {
                        mSituation.setText(entry.getSituation());
                        if (mSituation.getText() != null) {
                            mSituation.setSelection(mSituation.getText().length());
                        }
                        mThoughts.setText(entry.getThoughts());
                        mEmotions.setText(entry.getEmotion());
                        mReaction.setText(entry.getReaction());
                        Date date = new Date();
                        date.setTime(entry.getDate());
                        mDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date));
                    }
                }
            });

            TextView dateTextView = findViewById(R.id.txt_date);
            dateTextView.setVisibility(View.VISIBLE);
            mDate.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        mSituation = findViewById(R.id.edit_situation);
        mThoughts = findViewById(R.id.edit_thoughts);
        mEmotions = findViewById(R.id.edit_emotions);
        mReaction = findViewById(R.id.edit_reaction);
        mDate = findViewById(R.id.edit_date);
        mSaveButton = findViewById(R.id.btn_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });
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
            case R.id.menu_action_delete:
                if (mEntryId != DEFAULT_ENTRY_ID) {
                    mRepository.delete(mEntryId);
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean saveEntry() {
        if (mEntryId != DEFAULT_ENTRY_ID) {
            Entry entry = mLiveData.getValue();
            if (entry != null) {
                entry.setSituation(mSituation.getText().toString());
                entry.setThoughts(mThoughts.getText().toString());
                entry.setEmotion(mEmotions.getText().toString());
                entry.setReaction(mReaction.getText().toString());
                mRepository.update(entry);
            }
        } else {
            Entry entry = new Entry();
            entry.setSituation(mSituation.getText().toString());
            entry.setThoughts(mThoughts.getText().toString());
            entry.setEmotion(mEmotions.getText().toString());
            entry.setReaction(mReaction.getText().toString());
            entry.setDate(new Date().getTime());
            mRepository.insert(entry);
        }
        finish();
        return true;
    }
}
