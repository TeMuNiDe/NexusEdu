package com.vitech.nexusedu;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.vitech.nexusedu.data.Question;
import com.vitech.nexusedu.data.QuestionBase;
import com.vitech.nexusedu.data.QuestionFragment;
import com.vitech.nexusedu.data.Test;
import com.vitech.nexusedu.data.TestBase;

import java.util.ArrayList;


public class TestActivity extends AppCompatActivity {
    TextView timer;
    int score = 0;
    long time = 0;
    static int VALIDATE = 0;
    static int NEXT = 1;
    CountDownTimer clock;
    FloatingActionButton next;
    TextView banner;
    QuestionFragment fragment;


    FragmentTransaction transaction;
    int currentAnswer = 0;
    FragmentManager manager;
    View questionContainer;
    int question = -1;
    int clickType = NEXT;
    Test test;
    ArrayList<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        manager = getSupportFragmentManager();

        banner = (TextView)findViewById(R.id.banner);

        questionContainer = findViewById(R.id.question_frame);
        timer = (TextView)findViewById(R.id.timer);
        next  = (FloatingActionButton) findViewById(R.id.next);
        test = getIntent().getParcelableExtra("test");
        setTitle(test.title);
        Log.d("test",test.question_set+":::"+test.title);
        questions = new QuestionBase(getApplicationContext()).getQuestions(test.question_set);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(question==-1){
            banner.setVisibility(View.GONE);
            questionContainer.setVisibility(View.VISIBLE);
Log.d("Question Started","true");

transaction = manager.beginTransaction();
          fragment = new QuestionFragment();
            Bundle args = new Bundle();
            args.putParcelable("question",questions.get(++question));
            fragment.setArguments(args);
transaction.replace(R.id.question_frame,fragment).commit();
            startCountDown();
            clickType=VALIDATE;
            next.setImageResource(R.drawable.submit_answer);
        }
        else if(clickType==VALIDATE){
            Log.d("option",Question.convertAnswer(currentAnswer));

               if(fragment.answerSubmitted()){
                   score++;
               }
            clickType = NEXT;
            next.setImageResource(R.drawable.next_button);

        }
        else if(question<questions.size()-1){
            transaction  = manager.beginTransaction();
           fragment = new QuestionFragment();
            Bundle args = new Bundle();
            args.putParcelable("question",questions.get(++question));
            fragment.setArguments(args);
            transaction.replace(R.id.question_frame,fragment).commit();
            clickType=VALIDATE;
            next.setImageResource(R.drawable.submit_answer);
        }
        else if(question==questions.size()-1) {
            clock.cancel();
            question++;
if(Integer.parseInt(test.top_score)<score){
    test.top_score = Integer.toString(score);
    test.time = Long.toString(time);
    new TestBase(getApplicationContext()).updateSet(test);

}
            banner.setText("Your Score is" +
                    " "+Integer.toString(score));
            banner.setVisibility(View.VISIBLE);
            questionContainer.setVisibility(View.GONE);

        }
        else {
            onBackPressed();
        }


    }
});
    }

    void startCountDown(){
        final int period = 30*60*1000;
        clock = new CountDownTimer(period,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText(formatTime(period-millisUntilFinished));
                time = period-millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Time UP",Toast.LENGTH_LONG).show();
            }
        };
        clock.start();

    }
    static String formatTime(long mills){
        long sec = mills/1000;
        long secc = sec%60;
        long min = sec/60;
        return String.format("%02d:%02d",min,secc);
    }



}

