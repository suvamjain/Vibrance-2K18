package com.example.ayush.vibrance18.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.vibrance18.R;
import com.example.ayush.vibrance18.activity.server;
import com.example.ayush.vibrance18.other.EventAdapter;
import com.example.ayush.vibrance18.other.Events;
import com.example.ayush.vibrance18.other.HomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements HomeAdapter.onItemClickListener{



    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private HomeAdapter adapter;
    private List<Events> data_list;
    public static final String key = "PASS_DATA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the intro_frag3 for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();



        recyclerView = getView().findViewById(R.id.home_recycler);
        data_list = new ArrayList<>();

        get_data(0);

        gridCreate(1);



    }

    public void gridCreate(int span)
    {
        gridLayoutManager = new GridLayoutManager(getActivity(),span,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(getActivity(),data_list);
        recyclerView.setAdapter(adapter);

        adapter.setOnClick(HomeFragment.this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1)
                {
                    get_data(data_list.get(data_list.size()-1).getSln()+1);
                }
            }
        });
    }




    private void get_data(final int id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request= new Request.Builder().url("http://"+ server.server_ip()+"/Vib/App/AllEvent.php?id="+id).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array=new JSONArray(response.body().string());
                    int s=id;
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);

                        Events data= new Events(s++,object.getString("idevent"),object.getString("eventname"),object.getString("eventdesc"),
                                object.getString("eventtime"),object.getString("eventdate"),object.getString("venue"),object.getString("fee"),
                                object.getString("schoolid"), object.getString("clubid"), object.getString("teamsize"),object.getString("istrending"));

                        data_list.add(data);
                        Log.d("id",Integer.toString(array.length()));
                        Log.d("My List  ",array.toString());
                        Log.d("......times....",Integer.toString(i));
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
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

    @Override
    public void onclick(int position) {

    }

    public interface OnFragmentInteractionListener {
    }

   /* @Override
    public void onclick(int position) {
        Intent details = new Intent(this,Details.class);

        Events clickedItem = data_list.get(position);
        Log.d("...Value..",data_list.get(position).getEventname().toUpperCase());
        details.putExtra(key,clickedItem);
        startActivity(details);
    }*/
}
