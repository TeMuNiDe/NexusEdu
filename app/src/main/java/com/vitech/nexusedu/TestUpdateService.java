package com.vitech.nexusedu;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vitech.nexusedu.data.Question;
import com.vitech.nexusedu.data.QuestionBase;
import com.vitech.nexusedu.data.Test;
import com.vitech.nexusedu.data.TestBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestUpdateService extends IntentService {
OkHttpClient client;
    int qc=0;
    ArrayList<Test> tests,localTests;
    TestBase testBase;
    QuestionBase questionBase;
    ArrayList<Question> localQuestions;
    ArrayList<Question> questions;
    public TestUpdateService() {
        super("testUpdateService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Log.d("Service","started");
    tests = new ArrayList<>();
        questions = new ArrayList<>();
        testBase = new TestBase(getApplicationContext());
         localTests = testBase.getTests();

     questionBase = new QuestionBase(getApplicationContext());
        localQuestions = questionBase.getQuestions();

   final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("tests");
try{

    reference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
           Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()){
                DataSnapshot snapshot = iterator.next();
tests.add(new Test(snapshot.child("title").getValue().toString(),snapshot.child("question_set").getValue().toString(),null,null));
            }

            for(int p = localTests.size();p<tests.size();p++){
                testBase.insertTest(tests.get(p));
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

final DatabaseReference qreference = database.getReference("questions");

    qreference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()){
                DataSnapshot snapshot = iterator.next();
                Log.d("refq",Boolean.toString(snapshot.child("set").exists()));
                questions.add(new Question(snapshot.child("set").getValue().toString(),snapshot.child("statement").getValue().toString(),snapshot.child("option_a").getValue().toString(),snapshot.child("option_b").getValue().toString(),snapshot.child("option_c").getValue().toString(),snapshot.child("option_d").getValue().toString(),snapshot.child("correct_answer").getValue().toString()));
            }

            for(int p = localQuestions.size();p<questions.size();p++){
                questionBase.insertQuestion(questions.get(p));

            }
            sendBroadcast(new Intent(TestBoard.SYNC_COMPLETE));

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


        }catch (Exception e){
            sendBroadcast(new Intent(TestBoard.SYNC_ERROR));
            e.printStackTrace();
            return;


        }
       // Log.d("Sync Complete","inserted_tests:::"+Integer.toString(tests.size())+":::insterted_questions::"+Integer.toString(qc));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service","destroyed");
    }
}
