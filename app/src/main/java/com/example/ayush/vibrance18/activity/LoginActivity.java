package com.example.ayush.vibrance18.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.ayush.vibrance18.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    Button login,register,forgotpass;
    EditText user,pass;
    SharedPreferences prefs;

    SharedPreferences.Editor editor;
    public static final String myprefs = "login.conf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.loginmain);
        register=findViewById(R.id.registermain);
        user=findViewById(R.id.usernamelogin);
        pass=findViewById(R.id.passwordlogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                HashMap data=new HashMap();
                data.put("username",user.getText().toString());
                data.put("password",pass.getText().toString());
                prefs=getSharedPreferences(myprefs, Context.MODE_PRIVATE);
                editor=prefs.edit();
                PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if(!s.contains("failed")){
                            editor.putString("username",user.getText().toString());
                            editor.putString("password",pass.getText().toString());
                            editor.putString("Name",s);
                            editor.apply();
                            Intent launchNextActivity;
                            launchNextActivity = new Intent(LoginActivity.this, MainActivity.class);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(launchNextActivity);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://"+server.server_ip()+"/Vib/App/Login.php");
            }
        });

    }

}
