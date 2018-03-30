package com.recyclerview.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Naman Khurpia on 19-02-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<dataprovider>arrayList=new ArrayList<dataprovider>();

    public RecyclerAdapter(ArrayList<dataprovider> arrayList)
    {

        this.arrayList=arrayList;

    }




    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdescription,parent,false);

        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        dataprovider dataprovider=arrayList.get(position);
        holder.imageView.setImageResource(dataprovider.getImg_res());
        holder.f_name.setText(dataprovider.getF_name());
        holder.d_name.setText(dataprovider.getD_name());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView f_name,d_name;


        public RecyclerViewHolder(View view)
        {
            super(view);
            imageView=(ImageView)view.findViewById(R.id.img);
            f_name=(TextView)view.findViewById(R.id.moviename);
            d_name=(TextView)view.findViewById(R.id.description);



        }
    }


}
