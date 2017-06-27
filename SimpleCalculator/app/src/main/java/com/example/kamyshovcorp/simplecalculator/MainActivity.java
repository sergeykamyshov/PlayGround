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
     *
     * @param view view that was clicked
     */
    public void clearResult(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewResult.setText("");
    }

    /**
     * Clear last input number or symbol
     *
     * @param view view that was clicked
     */
    public void clearLastInput(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        String result = textViewResult.getText().toString();
        if (!result.isEmpty()) {
            textViewResult.setText(result.substring(0, result.length() - 1));
        }
    }

    /**
     * Добавляет знак операции к введенному числу.
     * Если это вторая операция, то происходит вычисление результата и только потом добавляется
     * знак последней выбранной операции.
     * @param view  кнопка операции, которая была нажата
     */
    public void addOperation(View view) {
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        String result = textViewResult.getText().toString();

        // Нельзя вызывать операцию без введенных значений
        if (result.isEmpty()) {
            return;
        }

        // Если была указана какая-то операция, то выполняем ее
        for (String operation : basicOperations) {
            if (result.contains(operation)) {
                result = executeOperation(result, operation);
                break;
            }
        }
        // Добавляем указанную операцию к результату
        result += ((Button) view).getText();
        textViewResult.setText(result);
    }

    private String executeOperation(String result, String operation) {
        String[] tokens = result.split(getOperationForSplit(operation));
        Integer a = Integer.valueOf(tokens[0]);
        Integer b = Integer.valueOf(tokens[1]);
        int resultInt = 0;
        if ("+".equals(operation)) {
            resultInt = a + b;
        } else if ("-".equals(operation)) {
            resultInt = a - b;
        } else if ("*".equals(operation)) {
            resultInt = a * b;
        } else if ("/".equals(operation)) {
            resultInt = a / b;
        }
        return String.valueOf(resultInt);
    }

    private String getOperationForSplit(String operation) {
        if ("+".equals(operation)) {
            return "\\+";
        } else if ("-".equals(operation)) {
            return "\\-";
        } else if ("*".equals(operation)) {
            return "\\*";
        } else if ("/".equals(operation)) {
            return "\\/";
        }
        return "";
    }
}
