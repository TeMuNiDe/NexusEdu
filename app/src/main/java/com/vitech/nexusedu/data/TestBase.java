package com.vitech.nexusedu.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class TestBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "TESTS";
    public static final String TABLE_NAME = "TESTS";;
SQLiteDatabase db;
    public TestBase(Context context) {
        super(context,DB_NAME, null,1);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(TITLE TEXT,QUESTION_SET TEXT,TOP_SCORE TEXT,TIME_TAKEN TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Test> getTests(){
        ArrayList<Test> tests = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM TESTS WHERE 1",null);
        while (cursor.moveToNext()){

            tests.add(new Test(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
        }

        return  tests;
    }
    public void insertTest(Test test)
    {
        ContentValues v = new ContentValues();
        v.put("TITLE",test.title);
        v.put("QUESTION_SET",test.question_set);
        v.put("TOP_SCORE","0");
        v.put("TIME_TAKEN","0");
        db.insert("TESTS",null,v);
    }
public void updateSet(Test test){
    ContentValues v = new ContentValues();
    v.put("TOP_SCORE",test.top_score);
    v.put("TIME_TAKEN",test.time);
    db.update("TESTS",v," QUESTION_SET LIKE '"+test.question_set+"'",null);
}
}
