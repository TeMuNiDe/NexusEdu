package com.vitech.nexusedu.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.vitech.nexusedu.R;

/**
 * Created by varma on 27-03-2017.
 */

public class Question implements Parcelable {
    public String question_set;
    public String statement;
    public String option_a;
    public String option_b;
    public String option_c;
    public String option_d;
    public String correct_answer;
    public Question(String question_set,String statement,String option_a,String option_b,String option_c,String option_d,String correct_answer){
       this.question_set = question_set;
        this.statement = statement;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.correct_answer = correct_answer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeString(question_set);
        dest.writeString(statement);
        dest.writeString(option_a);
        dest.writeString(option_b);
        dest.writeString(option_c);
        dest.writeString(option_d);
        dest.writeString(correct_answer);

    }

   public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
       @Override
       public Object createFromParcel(Parcel source) {
           return new Question(source);
       }

       @Override
       public Object[] newArray(int size) {
           return new Question[size];
       }
   };
    public Question(Parcel parcel){
        this.question_set=parcel.readString();
        this.statement = parcel.readString();
        this.option_a = parcel.readString();
        this.option_b = parcel.readString();
        this.option_c = parcel.readString();
        this.option_d = parcel.readString();
        this.correct_answer = parcel.readString();
    }
  public  static String  convertAnswer(int p){
        switch (p){
            case R.id.option_a:return "a";
            case R.id.option_b:return "b";
            case R.id.option_c:return "c";
            case R.id.option_d:return "d";


        }
       return "0";
    }
    public int getCorrectId(){
        switch (correct_answer){
            case "a":return R.id.option_a;
            case "b":return R.id.option_b;
            case "c":return R.id.option_c;
            case "d":return R.id.option_d;
        }
        return 0;
    }
}
