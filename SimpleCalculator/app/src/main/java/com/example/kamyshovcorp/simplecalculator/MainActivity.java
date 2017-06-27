package com.example.kamyshovcorp.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> basicOperations = Arrays.asList("+", "-", "*", "/");

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
        String result = textViewResult.getText().toString();
        if (!result.isEmpty()) {
            textViewResult.setText(result.substring(0, result.length() - 1));
        }
    }

    public void countResult(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        String result = textViewResult.getText().toString();

        // You can not start input with operation
        if (result.isEmpty()) {
            return;
        }

        String lastSymbol = "";
        if (!result.isEmpty()) {
            lastSymbol = result.substring(result.length() - 1, result.length());
        }
        // If last symbol the operation then change the operation for new
        if (basicOperations.contains(lastSymbol)) {
            textViewResult.setText(result.substring(0, result.length() - 1));
            textViewResult.setText(textViewResult.getText().toString() + ((Button) view).getText().toString());
        } else {
            textViewResult.setText(textViewResult.getText().toString() + ((Button) view).getText().toString());
        }
    }
}
