package com.example.ijp_mbp.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Stack;

public class CalcLogic {

    private int currentResult;
    private Stack<Integer> numbers;
    private String operator;
    private Context context;
    public CalcView calcView;

    public CalcLogic(Context context, View view, TextView textView) {
        currentResult = 0;
        numbers = new Stack<>();
        operator = "null";
        this.context = context;
        calcView = new CalcView(context, view, textView);
    }

    public boolean isNumber (String string) {
        switch (string) {
            case "1" :
            case "2" :
            case "3" :
            case "4" :
            case "5" :
            case "6" :
            case "7" :
            case "8" :
            case "9" :
            case "0" : return true;
            default : return false;

        }
    }

    public String lastDigit (String string) {
        if(string.length() > 0) {
            return string.substring(string.length()-1);
        } else {
            return "";
        }

    }
    public boolean isOperator (String string) {
        switch (string) {
            case "+" :
            case "-" :
            case "X" :
            case "/" :
            case "%" : return true;
            default : return false;

        }
    }

    public int operate (int first, int second, String symbol) {
        switch (symbol) {
            case "+" : return first + second;
            case "-" : return first - second;
            case "X" : return first * second;
            case "/" : return first / second;
            case "%" : return (int) (first % second);
            default: return 123;
         }
    }

    public void HandleNumberPress(int num) {
        String currentResult = calcView.getCurrentResult();
        if(isNumber(lastDigit(currentResult))) {
            calcView.appendToResult(num);
        } else {
            calcView.setResult(String.valueOf(num));
        }

    }


    public void HandleOperatorPress(String operator) {
        try {
            if (this.operator.equals("null")) {
                String currentResult = calcView.getCurrentResult();
                if (isNumber(lastDigit(currentResult))) {
                    numbers.push(Integer.valueOf(currentResult));
                }
                this.operator = operator;
            } else {
                int x = Integer.valueOf(calcView.getCurrentResult());
                int y = numbers.pop();
                numbers.push(operate(y, x, operator));
            }
            calcView.setResult(operator);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void HandleSpecialPress(String operator) {
        if(operator.equals("=")) {
            int x = Integer.valueOf(calcView.getCurrentResult());
            int y = numbers.pop();
            numbers.push(operate(y, x, this.operator));
            calcView.setResult(String.valueOf(numbers.peek()));
        } else if (operator.equals("C")){
            this.operator = "null";
            numbers.clear();
            calcView.setResult("0");
        } else if (operator.equals("DEL")) {
            String currentResult = calcView.getCurrentResult();
            if(currentResult.length() > 0){
                calcView.setResult(currentResult.substring(0, currentResult.length()-1));
            }
        }
    }

    public void handleButtonClick(View view) {
        Button button = (Button) view;
        String symbol = (String) button.getText();

        if(isNumber(symbol)) {
            HandleNumberPress(Integer.valueOf(symbol));
        } else if(isOperator(symbol)){
            HandleOperatorPress(symbol);
        } else {
            HandleSpecialPress(symbol);
        }

        calcView.setCellActive(view);
    }
}
