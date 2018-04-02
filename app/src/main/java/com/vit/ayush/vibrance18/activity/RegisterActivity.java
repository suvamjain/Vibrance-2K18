package com.vit.ayush.vibrance18.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vit.ayush.vibrance18.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText name,username,password,phone,email,college;
    Button loginbtn,registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name= findViewById(R.id.namereg);
        username = findViewById(R.id.usernameregister);
        password = findViewById(R.id.passwordregister);
        phone = findViewById(R.id.phoneregister);
        email = findViewById(R.id.emailregister);
        college = findViewById(R.id.collegeregister);
        loginbtn = findViewById(R.id.loginbtnreg);
        registerbtn = findViewById(R.id.registerbtnreg);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                HashMap data=new HashMap();
                data.put("name",name.getText().toString());
                data.put("username",username.getText().toString());
                data.put("password",password.getText().toString());
                data.put("phone",phone.getText().toString());
                data.put("email",email.getText().toString());
                data.put("college",college.getText().toString());
                PostResponseAsyncTask task = new PostResponseAsyncTask(RegisterActivity.this, data, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d("..Message Recieved::",s);
                        if(s.trim().equals("user")){
                            Toast.makeText(RegisterActivity.this, "Username exists", Toast.LENGTH_SHORT).show();

                        }
                        else if(s.trim().equals(("server"))){
                            Toast.makeText(RegisterActivity.this, "Server Error Plz try again later", Toast.LENGTH_SHORT).show();
                        }
                        else if(s.trim().equals(("phone"))){
                            Toast.makeText(RegisterActivity.this, "Phone Number already in use", Toast.LENGTH_SHORT).show();
                        }
                        else if(s.trim().equals(("email"))){
                            Toast.makeText(RegisterActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                        }
                        else if(s.trim().equals("success")){
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            Intent launchNextActivity;
                            launchNextActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(launchNextActivity);
                        }
                    }
                });
                Log.d(".....Check:::",server.server_ip()+"Appregister.php");
                task.execute(server.server_ip()+"DB_Service/Appregister.php");
            }
        });


    }

}
