package com.example.ayush.vibrance18.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.vibrance18.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private Context context;
    private List<Events> my_data;
    private onItemClickListener mListener;
    int prevpos=0;

    public interface onItemClickListener{
        void onclick(int position);
    }

    public void setOnClick(onItemClickListener listener)
    {
        mListener=listener;
    }

    public HomeAdapter(Context context, List<Events> data_list) {
        this.context=context;
        this.my_data=data_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.homecard,parent,false);

        return new ViewHolder(itemview);
    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(my_data.get(position).getEventname());

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.trend_img);
            name=itemView.findViewById(R.id.trend_name);


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

