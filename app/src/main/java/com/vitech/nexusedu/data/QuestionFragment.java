package com.vitech.nexusedu.data;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vitech.nexusedu.R;


public class QuestionFragment extends Fragment {

    AnswerButton option_a,option_b,option_c,option_d;
    TextView statement;
    RadioGroup answer;
    View view;
        Question question;

    public QuestionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tent =
        inflater.inflate(R.layout.fragment_question, container, false);
        this.view = tent;
        statement = (TextView)tent.findViewById(R.id.statement);
        answer=(RadioGroup)tent.findViewById(R.id.answer);
question = getArguments().getParcelable("question");
        option_a = (AnswerButton)tent.findViewById(R.id.option_a);
        option_b =(AnswerButton)tent.findViewById(R.id.option_b);
        option_c = (AnswerButton)tent.findViewById(R.id.option_c);
        option_d = (AnswerButton)tent.findViewById(R.id.option_d);
        statement.setText(question.statement);
        option_a.setText(question.option_a);
        option_b.setText(question.option_b);
        option_c.setText(question.option_c);
        option_d.setText(question.option_d);


        return tent;
    }


 public boolean answerSubmitted(){

        if(question.getCorrectId()==answer.getCheckedRadioButtonId()){
            view.findViewById(question.getCorrectId()).setBackgroundColor(getResources().getColor(R.color.answer_correct));
            return true;
        }
        else {
            Log.d("checked",Integer.toString(answer.getCheckedRadioButtonId()));
            if(answer.getCheckedRadioButtonId()!=-1  ) {
                view.findViewById(answer.getCheckedRadioButtonId()).setBackgroundColor(getResources().getColor(R.color.answer_wrong));


            }
            view.findViewById(question.getCorrectId()).setBackgroundColor(getResources().getColor(R.color.answer_correct));
return false;
        }

    }

  }
