package com.vitech.nexusedu.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Test implements Parcelable{
    public String title;
    public String question_set;
    public String top_score;
    public String time;
    public Test(String title,String question_set, String top_score, String time){
        this.title = title;
        this.question_set = question_set;
        this.top_score = top_score;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Test[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeString(title);
        dest.writeString(question_set);
        dest.writeString(top_score);
dest.writeString(time);
    }
    public Test(Parcel parcel){

        this.title = parcel.readString();
        this.question_set = parcel.readString();
        this.top_score = parcel.readString();
        this.time = parcel.readString();
    }
}
