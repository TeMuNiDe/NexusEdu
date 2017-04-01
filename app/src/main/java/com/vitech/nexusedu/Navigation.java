package com.vitech.nexusedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Navigation extends AppCompatActivity {
    View loginContainer ;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_navigation);
      preferences = getSharedPreferences("sprf",MODE_PRIVATE);
        if(preferences.getBoolean("auth",false)){
            startActivity(new Intent(getApplicationContext(),TestBoard.class));
        }




        getSupportActionBar().hide();
        loginContainer = findViewById(R.id.login_container);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a = AnimationUtils.loadAnimation(Navigation.this,R.anim.logo_animation);
                a.setInterpolator(new DecelerateInterpolator());
                a.setDuration(1000);
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        loginContainer.setVisibility(View.VISIBLE);
                        Animation b  = AnimationUtils.loadAnimation(Navigation.this,R.anim.slide_up);
                        b.setInterpolator(new DecelerateInterpolator());
                        b.setDuration(1000);
                        loginContainer.startAnimation(b);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                findViewById(R.id.brand_logo).startAnimation(a);
            }
        },1000);

        findViewById(R.id.log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText u = (EditText)findViewById(R.id.user_name);
                EditText p = (EditText)findViewById(R.id.paswrd);
                if((!u.getText().toString().equals("2073"))||p.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }else {
                    new Login().execute(p.getText().toString());
                }
            }
        });

    }

    class Login extends AsyncTask<String,String,String>{
ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
          dialog = new ProgressDialog(Navigation.this);
            dialog.setIndeterminate(false);
            dialog.setMessage("Loggin In... Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(final String... params) {
            String rp  =  "0";
            try {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference  reference = database.getReference("passwords");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
                        while (iterator.hasNext()){

                           // Log.d(iterator.next().child("password").getValue().toString(),iterator.next().child("status").getValue().toString());
                        DataSnapshot current = iterator.next();
                            if(current.child("password").getValue().toString().equals(params[0])&&current.child("status").getValue().toString().equals("active")){

                                onPostExecute("1");

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }catch (Exception e){
return "0";
            }
            return  "3";
        }

        @Override
        protected void onPostExecute(String aBoolean) {


            if(aBoolean.equals("1")){
                dialog.cancel();
                Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = preferences.edit();
                startService(new Intent(getApplicationContext(),TestUpdateService.class));
                editor.putBoolean("auth",true);
                editor.commit();
                startActivity(new Intent(getApplicationContext(),TestBoard.class));
            }else if(aBoolean.equals("0")){
                dialog.cancel();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

            }

            super.onPostExecute(aBoolean);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
