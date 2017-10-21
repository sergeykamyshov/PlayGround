package ru.kamyshovcorp.weekplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.kamyshovcorp.weekplanner.R;
import ru.kamyshovcorp.weekplanner.model.Card;
import ru.kamyshovcorp.weekplanner.model.Task;

public class CardActivity extends AppCompatActivity {

    public static final String EXTRA_CARD = "card";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Card card = extras.getParcelable(EXTRA_CARD);

        fillCardTitle(card);
        fillTasksList(card);
    }

    private void fillCardTitle(Card card) {
        EditText cardTitleEditText = (EditText) findViewById(R.id.card_title);
        cardTitleEditText.setText(card.getTitle());
    }

    private void fillTasksList(Card card) {
        LinearLayout cardLayout = (LinearLayout) findViewById(R.id.card_layout);
        for (Task task : card.getTasks()) {
            createTaskItemLayout(cardLayout, task);
        }
    }

    private void createTaskItemLayout(LinearLayout linearLayout, Task task) {
        LinearLayout taskLayout = new LinearLayout(this);
        LinearLayout.LayoutParams taskParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskLayout.setLayoutParams(taskParams);
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox taskCheckBox = new CheckBox(this);
        taskCheckBox.setGravity(Gravity.TOP);
        taskCheckBox.setChecked(task.isDone());
        taskLayout.addView(taskCheckBox);

        TextView taskDescriptionTextView = new TextView(this);
        LinearLayout.LayoutParams taskDescriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        taskDescriptionParams.gravity = Gravity.CENTER;
        taskDescriptionTextView.setLayoutParams(taskDescriptionParams);
        taskDescriptionTextView.setText(task.getDescription());
        taskLayout.addView(taskDescriptionTextView);

        linearLayout.addView(taskLayout);
    }
}
