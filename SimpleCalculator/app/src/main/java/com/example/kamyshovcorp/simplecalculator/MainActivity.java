package com.example.kamyshovcorp.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean operationWasInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addNumber(View view) {
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        Button numberButton = (Button) view;
        String number = numberButton.getText().toString();
        if (operationWasInput) {
            resultTextView.setText(number);
        } else {
            resultTextView.setText(resultTextView.getText() + number);
        }
    }

    public void addOperation(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.resultTextView);
        String inputNumber = textViewResult.getText().toString();
        // Cann't call operation sign for empty input
        if (inputNumber.isEmpty()) {
            return;
        }

        Button operationButton = (Button) view;
        String operationSign = operationButton.getText().toString();
        // Move input number and sign to upper TextView
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        operationTextView.setText(inputNumber + " " + operationSign);

        operationWasInput = true;
    }

    public void calculate(View view) {
        // Parse upper TextView for number and operation sign
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        String[] split = operationTextView.getText().toString().split(" ");
        int x = Integer.valueOf(split[0]);
        String operationSign = split[1];

        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        int y = Integer.valueOf(resultTextView.getText().toString());

        operationTextView.setText("");
        String result = executeOperation(x, y, operationSign);
        resultTextView.setText(result);
    }

    private String executeOperation(int x, int y, String operation) {
        int resultInt = 0;
        if ("+".equals(operation)) {
            resultInt = x + y;
        } else if ("-".equals(operation)) {
            resultInt = x - y;
        } else if ("*".equals(operation)) {
            resultInt = x * y;
        } else if ("/".equals(operation)) {
            resultInt = x / y;
        }
        return String.valueOf(resultInt);
    }

    /**
     * Clear the input result
     *
     * @param view view that was clicked
     */
    public void clearResult(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.resultTextView);
        textViewResult.setText("");
    }

    /**
     * Clear last input number or symbol
     *
     * @param view view that was clicked
     */
    public void clearLastInput(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.resultTextView);
        String result = textViewResult.getText().toString();
        if (!result.isEmpty()) {
            textViewResult.setText(result.substring(0, result.length() - 1));
        }
    }
}
