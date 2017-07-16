package com.example.kamyshovcorp.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

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
        // Нельзя добавлять операцию пока ничего не ввели
        if (mResultTextView.getText().toString().isEmpty()) {
            return;
        }

        if (mOperationTextView.getText().toString().isEmpty()) {
            Button operationButton = (Button) view;
            String operationSign = operationButton.getText().toString();
            mOperationTextView.setText(mResultTextView.getText().toString() + " " + operationSign);
            mResultTextView.setText("");
            return;
        }

        // Выполняет операцию, чтобы лишний раз не нажимать кнопку равно
        String[] split = mOperationTextView.getText().toString().split(" ");
        BigDecimal x = new BigDecimal(split[0]);
        String operationSign = split[1];

        BigDecimal y = new BigDecimal(mResultTextView.getText().toString());

        BigDecimal result = MathHelper.executeOperation(x, y, operationSign);
        Button operationButton = (Button) view;
        String pressButtonOperationSign = operationButton.getText().toString();
        mOperationTextView.setText(result + " " + pressButtonOperationSign);

        mResultTextView.setText("");
    }

    public void calculate(View view) {
        if (mResultTextView.getText().toString().isEmpty()) {
            if (!mOperationTextView.getText().toString().isEmpty()) {
                String[] split = mOperationTextView.getText().toString().split(" ");
                mResultTextView.setText(split[0]);
                mOperationTextView.setText("");
                return;
            }
        } else if (mOperationTextView.getText().toString().isEmpty()) {
            return;
        }

        String[] split = mOperationTextView.getText().toString().split(" ");
        BigDecimal x = new BigDecimal(split[0]);
        String operationSign = split[1];

        BigDecimal y = new BigDecimal(mResultTextView.getText().toString());

        mOperationTextView.setText("");
        BigDecimal result = MathHelper.executeOperation(x, y, operationSign);
        mResultTextView.setText(result.toString());
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
