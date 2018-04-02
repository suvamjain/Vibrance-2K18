package com.example.ayush.vibrance18.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.vibrance18.R;
import com.example.ayush.vibrance18.activity.LoginActivity;
import com.example.ayush.vibrance18.activity.MainActivity;
import com.example.ayush.vibrance18.activity.server;
import com.example.ayush.vibrance18.other.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment{


    TextView name;
    TextView email;
    TextView phn;
    TextView eventscount;
    TextView teescount;
    Button logout;
    CardView cardView;
    SharedPreferences prefs;
    private List<User> data_list;
    public  String user;
    public  String mail;
    public  String phone;
    public  int countevents;
    public  int counttees;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        email=getView().findViewById(R.id.dispmail);
        teescount=getView().findViewById(R.id.teescount);
        phn=getView().findViewById(R.id.dispphn);
        logout = getView().findViewById(R.id.logoutprofile);
        eventscount=getView().findViewById(R.id.eventscount);
        cardView=getView().findViewById(R.id.cardViewprofile);
        prefs = this.getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        name=getView().findViewById(R.id.dispuser);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().clear().commit();
                Intent launchNextActivity;
                launchNextActivity = new Intent(getActivity(), LoginActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(getActivity(), "You have been logged out", Toast.LENGTH_LONG).show();
                startActivity(launchNextActivity);
            }
        });
        data_list = new ArrayList<User>();
        get_data(prefs.getString("username",""));




    }




    private void get_data(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String,Void,Void> task = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... strings) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://" + server.server_ip() + "/Vib/App/details.php?id=" + id).build();
                Request request2 = new Request.Builder().url("http://" + server.server_ip() + "/Vib/App/detailstee.php?id=" + id).build();

                try {
                    Response response = client.newCall(request).execute();
                    Response response2 = client.newCall(request2).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    JSONArray arraytee = new JSONArray(response2.body().string());
                    countevents=array.length()-1;
                    counttees = arraytee.length();
                    String s = id;
                    for (int i = 0; i < 1; i++) {
                        JSONObject object = array.getJSONObject(i);

                        user= object.getString("name");
                        mail = object.getString("email");
                        phone = object.getString("phno");
                        Log.d("id", Integer.toString(array.length()));
                        Log.d("My List  ", array.toString());
                        Log.d("......times....", Integer.toString(i));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {

                    System.out.print("End of Contents");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                name.setText(user);
                email.setText(mail);
                phn.setText(phone);
                eventscount.setText(Integer.toString(countevents));
                teescount.setText(Integer.toString(counttees));
                super.onPostExecute(aVoid);
            }
        };
        task.execute(id);

    }


}
