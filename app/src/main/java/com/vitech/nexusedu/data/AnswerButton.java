package com.vitech.nexusedu.data;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import com.vitech.nexusedu.R;


public class AnswerButton extends RadioButton {

    public AnswerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerButton(Context context) {
        super(context);
    }

    public AnswerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean checked) {

        Log.d("optcheck",Question.convertAnswer(getId())+":::"+Boolean.toString(checked));
        if(checked) {
            setBackgroundColor(getResources().getColor(R.color.answer_click));
        }
        else {
           setBackgroundColor(Color.argb(0,0,0,0));

        }
        super.setChecked(checked);
    }



}
