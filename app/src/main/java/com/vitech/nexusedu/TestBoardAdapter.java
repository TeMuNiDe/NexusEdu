package com.vitech.nexusedu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitech.nexusedu.data.Test;
import com.vitech.nexusedu.data.TestBase;

import java.util.ArrayList;

public class TestBoardAdapter extends RecyclerView.Adapter<TestBoardAdapter.TestHolder> {
    Context context;
   public ArrayList<Test> tests;
public TestBoardAdapter(Context context){
    this.context = context;
    tests = new TestBase(context).getTests();
}

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(LayoutInflater.from(context).inflate(R.layout.test_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        holder.test_no.setText(tests.get(position).title);
        holder.top_score.setText("Top Score : " + tests.get(position).top_score);
        holder.time_taken.setText(" in "+TestActivity.formatTime(Long.parseLong(tests.get(position).time)));

holder.test = tests.get(position);
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class TestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
TextView test_no,top_score,time_taken;
        Test test;
        public TestHolder(View itemView) {
            super(itemView);
            test_no = (TextView)itemView.findViewById(R.id.test_no);
            top_score = (TextView)itemView.findViewById(R.id.top_score);
            time_taken= (TextView)itemView.findViewById(R.id.top_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context,TestActivity.class).putExtra("test",test).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
