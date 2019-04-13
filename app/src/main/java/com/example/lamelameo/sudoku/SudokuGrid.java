package com.example.lamelameo.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.graphics.Rect;

public class SudokuGrid extends View {
    //TODO: could use this single view as only view for grid and get coordinates on click to determine which cell was
    //  clicked instead of using actual views for each cell

    private int value = 0;  // value currently set in position - 0 default = empty cell
    private int [] pencil_values;  // array for integers for pencilled in values
    private boolean editable = true;  // values on the board that can be changed
    private Paint mPaint;

    public SudokuGrid(Context context) {
        super(context);

        mPaint = new Paint();
    }

    public void setNotEditable() {
        editable = false;
    }

    public void setValue(int value) {
        // change value if is editable
        if(editable){
            this.value = value;
            invalidate();  // draws cell when value has changed
        }
    }

    public int getValue() {
        return value;
    }

    public void setPencil_values(int values[]) {
        if(editable){
            this.pencil_values =  values;
        }
        // draw cell if no value set - then pencil values are shown and have just been changed
        if(this.value == 0){
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNumber(canvas);
        //drawLines(canvas);
    }

    private void drawNumber(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(50);
        mPaint.setStyle(Paint.Style.FILL);
        Rect bounds = new Rect();
        mPaint.getTextBounds(String.valueOf(getValue()), 0, String.valueOf(getValue()).length(), bounds);

        if( getValue() != 0 ){
            canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width())/2, (getHeight() + bounds.height())/2	, mPaint);
            getWidth();
        }
    }
}
