package com.vitech.nexusedu.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by varma on 27-03-2017.
 */

public class QuestionBase extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public QuestionBase(Context context) {
        super(context, "QUESTIONS", null, 1);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QUESTIONS(QUESTION_SET TEXT,STATEMENT TEXT,OPTION_A TEXT,OPTION_B TEXT,OPTION_C TEXT,OPTION_D TEXT,CORRECT_ANSWER TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Question> getQuestions(String question_set) {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM QUESTIONS WHERE QUESTION_SET LIKE ?" ,new String[]{question_set});
        while (cursor.moveToNext()) {
            questions.add(new Question(question_set, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
        }


        return questions;

    }

    public void insertQuestion(Question question) {
        ContentValues contentValues  = new ContentValues();
        contentValues.put("QUESTION_SET",question.question_set);
        contentValues.put("STATEMENT",question.statement);
        contentValues.put("OPTION_A",question.option_a);
        contentValues.put("OPTION_B",question.option_b);
        contentValues.put("OPTION_C",question.option_c);
        contentValues.put("OPTION_D",question.option_d);
        contentValues.put("CORRECT_ANSWER",question.correct_answer);
        db.insert("QUESTIONS",null,contentValues);

    }

public ArrayList<Question> getQuestions(){
    ArrayList<Question> questions = new ArrayList<>();
    Cursor cursor = db.rawQuery("SELECT * FROM QUESTIONS WHERE 1" ,null);
    while (cursor.moveToNext()) {
        questions.add(new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
    }


    return questions;
}

}
