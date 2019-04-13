package com.example.lamelameo.sudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.text.DynamicLayout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

class SudokuCell extends View {

    private String TAG = "SudouCell";

    private int mTextColor = Color.BLACK;
    private TextPaint mTextPaint;
    private Paint mPaint;

    private float text12Sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private float text14Sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
    private Rect textBounds;
    private float screenDensity;
//    private Drawable background = getResources().getDrawable(R.drawable.cell_default, null);

    private String value;
    private ArrayList<Integer> pencilValues;
    private boolean showPencilValues;
//    private boolean isEditable;
//    private boolean redrawPencil;

    //constructor to create an instance of view
    public SudokuCell(Context context) {
        super(context);
        init(null, 0, context);
    }

    // constructor to inflate view from xml layout and attributes
    public SudokuCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, context);
    }

    public SudokuCell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, context);
    }

    private void init(AttributeSet attrs, int defStyle, Context context) {
        // Load attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuCell, defStyle,0);
        a.recycle();

        // make instance of paint
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        //TODO: can align text centre for easier coordinate choice
//        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mPaint = new Paint((Paint.ANTI_ALIAS_FLAG));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        textBounds = new Rect();
        screenDensity = getResources().getDisplayMetrics().density;

        // initialise values
        value = "";
        pencilValues = new ArrayList<>();
        showPencilValues = true;
//        redrawPencil = true;
//        isEditable = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw text for values
        drawValues(canvas);
        //TODO: have methods to draw value or pencil values... then call on click
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //TODO: dont know what this method is used for atm
    }

    private void drawValues(Canvas canvas) {
        // cell size is set to 37 x 37dp and text to 12/14sp so can use pixels from the methods that give their sizes
        // as the sizes will be scaled, however padding will be given converted from dp to px given screen density
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // fill in cell with White
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, mPaint);

        // draw cell outline
        float[] outline = {
                0, 0, canvasWidth, 0,
                canvasWidth, 0, canvasWidth, canvasHeight,
                0, canvasHeight, canvasWidth, canvasHeight,
                0, 0, 0, canvasHeight
        };
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1*screenDensity);  // 1dp line width (converted to pixels)
        canvas.drawLines(outline, mPaint);
        // TODO: draws crosshairs that intersect the middle of cell (for testing purposes)
//        mPaint.setColor(Color.CYAN);
//        canvas.drawLine(0f, canvasHeight/2f, canvasWidth, canvasHeight/2f, mPaint);
//        canvas.drawLine(canvasWidth/2f, 0f, canvasWidth/2f, canvasHeight, mPaint);
//        mPaint.setColor(Color.BLACK);

        Integer num5 = 5;
        String five = num5.toString();

        // draw value or pencil values depending on what is active...
        if (showPencilValues) {
            mTextPaint.setTextSize(text12Sp);

//            StaticLayout staticLayout =  new StaticLayout(five, mTextPaint, (int)mTextPaint.measureText(five),
//                    Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);

            //TODO: problem with centring 5 is that the bounds of the text is different using different methods
            // and idk where the drawtext coords are set to in relation to the text so positioning is shifted slightly
            // can use static/dynamic layout to get a fixed box and center the text inside of that instead

            // text height and width based on largest values for integers 1-9 lets all integers to be level
            // 5 has highest values for both (approx)
            mTextPaint.getTextBounds(five, 0, 1, textBounds);
            float textHeight = textBounds.height();
            float testWidth = textBounds.width();
            float textWidth = mTextPaint.measureText(five);
            Log.i(TAG, "5x: "+textWidth);
            Log.i(TAG, "5x bounds "+testWidth);
            Log.i(TAG, "5y: "+textHeight);

            // xy coords (bottom left) of the integer set in centre of cell (5) - gauge for all other cell coords
            float centreX = canvasWidth/2f - textWidth/2f;
            float centreY = canvasHeight/2f + textHeight/2f;

            // TODO: change so only the pencil value that needs editing is redrawn???
            for (Integer item : pencilValues) {
                String pencilText = item.toString();
                // not necessarily ordered - floor division to get row/col index (values in 3x3 grid layout in cell)
                double intRowIndex = Math.floor(item/3.1);
                double intColIndex = item - intRowIndex*3 - 1;

                // set pencil coordinates based on their x and y index in 3x3 grid with origin at the middle value, 5
                //  -1,-1   0,-1   1,-1
                //  -1, 0   0, 0   1, 0
                //  -1, 1   0, 1   1, 1
                double paddingX = 3.5 * screenDensity;  // padding between pencil values (dp converted to px)
                double paddingY = 1.5 * screenDensity;
                double pencilX = centreX + (textWidth + paddingX)*(intColIndex - 1);
                double pencilY = centreY + (textHeight + paddingY)*(intRowIndex - 1);

                Log.i("onDraw", "drawPencil x: "+(float)pencilX);
                Log.i("onDraw", "drawPencil y: "+(float)pencilY);
//                mTextPaint.bgColor = Color.BLUE;
                canvas.drawText(pencilText, (float)pencilX, (float)pencilY, mTextPaint);
            }

        } else { // draw value in centre of cell
            mTextPaint.setTextSize(text14Sp);
            // get text sizes and such
            int textWidth = (int)mTextPaint.measureText(value);
//            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            mTextPaint.getTextBounds(value, 0, value.length(), textBounds);
            int textHeight = textBounds.height();
            Log.i(TAG, "drawValue: "+textHeight);

            // TODO: origin for text is at bottom left corner
            final float textX = Math.round(canvasWidth*0.5f - textWidth*0.5f);
            final float textY = Math.round(canvasHeight*0.5f + textHeight*0.5f);

            canvas.drawText(value, textX, textY, mTextPaint);

//            CharSequence val = value;
//            DynamicLayout dynamicLayout = new DynamicLayout(val, mTextPaint, textWidth, Layout.Alignment.ALIGN_CENTER,
//                    1.0f, 0, false);
//            dynamicLayout.draw(canvas);

        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (value.equals("")) {  // if removed value then must show pencil values
            showPencilValues = true;
        } else {  // if added or changed value then show value
            showPencilValues = false;
        }
        invalidate();  // redraw cell for any value change
    }

    public ArrayList<Integer> getPencilValues() {
        return pencilValues;
    }

    public void editPencilValues(Integer pencilValue) {  //TODO: if use int it will think the given value is the index...
        if (pencilValues.contains(pencilValue)) {
            pencilValues.remove(pencilValue);
        } else {
            pencilValues.add(pencilValue);
        }
        if (showPencilValues) {  // if pencil values visible invalidate... else value is visible so no redrawing
            invalidate();
        }
    }

    public void clearPencilValues() {
        pencilValues.clear();
        if (showPencilValues) {  // only redraw if pencil values are visible

            invalidate();
        }
    }

    public void setShowPencilValues(boolean bool) {
        showPencilValues = bool;
    }

    public void setTextColor(@ColorInt int color) {
        mTextPaint.setColor(color);
    }

    public @ColorInt int getTextColor() {
        return mTextPaint.getColor();
    }

}
