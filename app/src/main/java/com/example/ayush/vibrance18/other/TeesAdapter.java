package com.example.ayush.vibrance18.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.vibrance18.R;
import com.example.ayush.vibrance18.activity.server;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TeesAdapter extends RecyclerView.Adapter<TeesAdapter.ViewHolder>{

    private Context context;
    private List<Tees> my_data;
    private onItemClickListener mListener;
    int prevpos=0;

    public interface onItemClickListener{
        void onclick(int position);
    }

    public void setOnClick(onItemClickListener listener)
    {
        mListener=listener;
    }

    public TeesAdapter(Context context, List<Tees> data_list) {
        this.context=context;
        this.my_data=data_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.teescard,parent,false);

        return new ViewHolder(itemview);
    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(my_data.get(position).getName());
        holder.price.setText(my_data.get(position).getPrice());
        URL url = null;
        Bitmap bmp=null;

        String x;
        x="http://"+ server.server_ip()+"/Vib/App/upload/"+my_data.get(position).getFront();
        Picasso.with(context).load(x).resize(250,300).into(holder.img);
        Log.d(".....img....",x);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView name;
        public TextView price;


        public ViewHolder(View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.teeimg);
            name=itemView.findViewById(R.id.teename);
            price=itemView.findViewById(R.id.teeprice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            mListener.onclick(position);
                        }
                    }
                }
            });
        }
    }


}

