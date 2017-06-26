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
}
