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
        Button signButton = (Button) view;
        String sign = signButton.getText().toString();
        mResultTextView.setText(mResultTextView.getText() + sign);
    }

    public void addDot(View view) {
        String text = mResultTextView.getText().toString();
        if (!text.isEmpty() && !text.contains(".")) {
            mResultTextView.setText(text + ".");
        }
    }

    public void addOperation(View view) {
        String inputNumber = mResultTextView.getText().toString();
        // Нельзя добавлять операцию пока ничего не ввели
        if (inputNumber.isEmpty()) {
            return;
        }

        Button operationButton = (Button) view;
        String operationSign = operationButton.getText().toString();
        mOperationTextView.setText(inputNumber + " " + operationSign);

        mResultTextView.setText("");
    }

    public void calculate(View view) {
        if (mResultTextView.getText().toString().isEmpty()) {
            if (!mOperationTextView.getText().toString().isEmpty()) {
                String[] split = mOperationTextView.getText().toString().split(" ");
                Double x = Double.valueOf(split[0]);
                // Если остаток по модулю 1 равен 0, то это целое число
                Double module = x % 1;
                if (Double.compare(0d, module) == 0) {
                    mResultTextView.setText(String.valueOf(x.intValue()));
                } else {
                    mResultTextView.setText(String.valueOf(x));
                }
                mOperationTextView.setText("");
            }
            return;
        } else if (mOperationTextView.getText().toString().isEmpty()) {
            return;
        }

        String[] split = mOperationTextView.getText().toString().split(" ");
        double x = Double.valueOf(split[0]);
        String operationSign = split[1];

        double y = Double.valueOf(mResultTextView.getText().toString());

        mOperationTextView.setText("");
        String result = executeOperation(x, y, operationSign);
        mResultTextView.setText(result);
    }

    private String executeOperation(double x, double y, String operation) {
        Double result = 0d;
        if ("+".equals(operation)) {
            result = x + y;
        } else if ("-".equals(operation)) {
            result = x - y;
        } else if ("*".equals(operation)) {
            result = x * y;
        } else if ("/".equals(operation)) {
            // На ноль делить нельзя
            if (Double.compare(0d, y) == 0) {
                result = 0d;
            } else {
                result = x / y;
            }
        }
        // Если остаток по модулю 1 равен 0, то это целое число
        Double module = result % 1;
        if (Double.compare(0d, module) == 0) {
            return String.valueOf(result.intValue());
        }
        return String.valueOf(result);
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
