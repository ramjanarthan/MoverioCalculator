package com.example.ijp_mbp.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcView {

    private Context context;
    private String currentResult;
    private View currentCell;
    private TextView currentDisplay;

    public CalcView(Context current, View view, View text) {
        this.context = current;
        this.currentCell = view;
        this.currentResult = "0";
        this.currentDisplay = (TextView) text;
        setResult(currentResult);
    }

    public void setResult (String newResult) {
        currentResult = newResult;
        currentDisplay.setText(newResult);
    }

    public void resetResult (){
        currentResult = "";
    }

    public void appendToResult (int number) {
        if (currentResult.equals("0")) {
            currentResult = String.valueOf(number);
        } else {
            currentResult = currentResult + String.valueOf(number);
        }
        currentDisplay.setText(currentResult);
    }

    public void setCurrentCell (View view){

        this.currentCell = view;
    }

    public String getCurrentResult() {

        return currentResult;
    }

    public void updateCellToActive (View view) {
        Button button = (Button) view;
        button.setBackgroundColor(context.getResources().getColor(R.color.activeCell));
        button.setTextColor(context.getResources().getColor(R.color.activeText));
    }

    public void updateCellToInactive (View view) {
        Button button = (Button) view;
        button.setBackgroundColor(context.getResources().getColor(R.color.inactiveCell));
        button.setTextColor(context.getResources().getColor(R.color.inactiveText));
    }

    //Call this in HandleButtonPress
    public void setCellActive (View view) {
        updateCellToInactive(currentCell);
        setCurrentCell(view);
        updateCellToActive(currentCell);
    }

}
