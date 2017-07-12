package com.example.kamyshovcorp.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private TextView mOperationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTextView = (TextView) findViewById(R.id.resultTextView);
        mOperationTextView = (TextView) findViewById(R.id.operationTextView);
    }

    public void addNumber(View view) {
        Button numberButton = (Button) view;
        String number = numberButton.getText().toString();
        mResultTextView.setText(mResultTextView.getText() + number);
    }

    public void addOperation(View view) {
        String inputNumber = mResultTextView.getText().toString();
        // Cann't call operation sign for empty input
        if (inputNumber.isEmpty()) {
            return;
        }

        Button operationButton = (Button) view;
        String operationSign = operationButton.getText().toString();
        // Move input number and sign to upper TextView
        mOperationTextView.setText(inputNumber + " " + operationSign);

        mResultTextView.setText("");
    }

    public void calculate(View view) {
        // Parse upper TextView for number and operation sign
        String[] split = mOperationTextView.getText().toString().split(" ");
        int x = Integer.valueOf(split[0]);
        String operationSign = split[1];

        int y = Integer.valueOf(mResultTextView.getText().toString());

        mOperationTextView.setText("");
        String result = executeOperation(x, y, operationSign);
        mResultTextView.setText(result);
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

    public void clearResult(View view) {
        mResultTextView.setText("");
        mOperationTextView.setText("");
    }

    public void clearLastInput(View view) {
        String result = mResultTextView.getText().toString();
        if (!result.isEmpty()) {
            mResultTextView.setText(result.substring(0, result.length() - 1));
        }
    }
}
