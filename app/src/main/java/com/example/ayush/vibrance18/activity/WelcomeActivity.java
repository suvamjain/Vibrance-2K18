package com.example.ayush.vibrance18.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ayush.vibrance18.R;
import com.example.ayush.vibrance18.intro.intro_main;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {
    LinearLayout l1,l2;
    SharedPreferences prefs;

    SharedPreferences.Editor editor;
    public static final String myprefs = "login.conf";

    Animation uptodown,downtoup;
    private static int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        prefs=getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String username = prefs.getString("username","");
                String password = prefs.getString("password","");
                HashMap data=new HashMap();
                data.put("username",username);
                data.put("password",password);
                if(!(username=="" && password=="")) {
                    PostResponseAsyncTask task = new PostResponseAsyncTask(WelcomeActivity.this, data, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (!s.contains("failed")) {
                                Intent launchNextActivity;
                                launchNextActivity = new Intent(WelcomeActivity.this, MainActivity.class);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(launchNextActivity);
                            } else {
                                Toast.makeText(WelcomeActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                Intent launchNextActivity;
                                launchNextActivity = new Intent(WelcomeActivity.this, LoginActivity.class);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(launchNextActivity);

                            }
                        }
                    });
                    task.execute("http://" + server.server_ip() + "/Vib/App/Login.php");

                }
                else{
                    Intent launchNextActivity;
                    launchNextActivity = new Intent(WelcomeActivity.this, intro_main.class);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(launchNextActivity);
                }
                finish();
            }
        }, TIME_OUT);

    }
}