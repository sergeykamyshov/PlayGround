package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Card;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD = "card";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Card card = extras.getParcelable(EXTRA_CARD);

        EditText cardTitleEditText = (EditText) findViewById(R.id.card_title);
        cardTitleEditText.setText(card.getTitle());
    }
}
