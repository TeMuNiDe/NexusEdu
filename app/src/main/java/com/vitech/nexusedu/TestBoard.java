package com.vitech.nexusedu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.vitech.nexusedu.data.TestBase;

public class TestBoard extends AppCompatActivity {
RecyclerView testBoard;
    public static final String SYNC_COMPLETE = "SyncComplete";
    public static final String SYNC_ERROR = "SyncError";
TestBoardAdapter adapter;
    SyncCompleteReceiver syncCompleteReceiver;
    SyncErrorReceiver syncErrorReceiver;
    SwipeRefreshLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_board);
testBoard = (RecyclerView)findViewById(R.id.test_board);
adapter = new TestBoardAdapter(getApplicationContext());
        testBoard.setAdapter(adapter);
        layout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        testBoard.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
startActivity(new Intent(getApplicationContext(),AboutUS.class));
    }
});
        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                                            emailIntent.setData(Uri.parse("mailto:krishnadeo450@gmail.com"));
                                                               emailIntent.putExtra(Intent.EXTRA_SUBJECT,"FeedBack- Nexus Edu App");
                                                               startActivity(Intent.createChooser(emailIntent,"Send Via"));
                                                           }
                                                       }
        );

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startService(new Intent(getApplicationContext(),TestUpdateService.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        adapter.tests = new TestBase(getApplicationContext()).getTests();
        adapter.notifyDataSetChanged();
        if(syncCompleteReceiver==null){
            syncCompleteReceiver = new SyncCompleteReceiver();
        }
        if(syncErrorReceiver==null){
            syncErrorReceiver = new SyncErrorReceiver();
        }
        IntentFilter filter = new IntentFilter(SYNC_COMPLETE);
        registerReceiver(syncCompleteReceiver,filter);
       IntentFilter filter2 = new IntentFilter(SYNC_ERROR);
        registerReceiver(syncErrorReceiver,filter2);
        super.onResume();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(syncCompleteReceiver);
        unregisterReceiver(syncErrorReceiver);
        super.onPause();
    }

    class SyncCompleteReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            layout.setRefreshing(false);
adapter.tests = new TestBase(context).getTests();
            adapter.notifyDataSetChanged();
        }
    }
    class SyncErrorReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            layout.setRefreshing(false);
            Snackbar.make(findViewById(R.id.activity_test_board),"Sync Error, Please Check Network Connection",Snackbar.LENGTH_LONG).show();
        }
    }
}
