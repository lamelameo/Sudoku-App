package com.example.lamelameo.sudoku;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioGroup;

public class CustomRadioButton extends AppCompatRadioButton {

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        // if button object is checked change to unchecked
        if(this.isChecked()) {
            // cant use setchecked = false due to the logic of radio buttons...have to clear radiogroup
            if(getParent() instanceof RadioGroup) {
                ((RadioGroup) getParent()).clearCheck();
            }
        } else {
            this.setChecked(true);
        }
//        Log.i("toggleRadioButton", "_"+this.isChecked());
    }
}
