package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ru.kamyshovcorp.weekplanner.R;

public class CardTitleActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_TITLE = "cardTitle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_title);

        // Настраиваем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_set_card_title);
        }

        Intent intent = getIntent();
        String currentCardTitle = intent.getStringExtra(EXTRA_CARD_TITLE);
        EditText cardTitleEditText = findViewById(R.id.txt_card_title);
        cardTitleEditText.setText(currentCardTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_title, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_apply_card_title:
                EditText cardTitleEditText = findViewById(R.id.txt_card_title);
                String cardTitle = cardTitleEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CARD_TITLE, cardTitle);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
