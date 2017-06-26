package com.example.kamyshovcorp.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void concatNumber(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        CharSequence number = ((Button) view).getText();
        textViewResult.setText(textViewResult.getText() + number.toString());
    }

    /**
     * Clear the input result
     * @param view  view that was clicked
     */
    public void clearResult(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewResult.setText("");
    }

    /**
     * Clear last input number or symbol
     * @param view  view that was clicked
     */
    public void clearLastInput(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        String text = textViewResult.getText().toString();
        if (!text.isEmpty()) {
            textViewResult.setText(text.substring(0, text.length() - 1));
        }
    }
}
