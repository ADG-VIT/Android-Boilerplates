package com.recyclerview.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] f_name,d_name;

    int[] Img_res={R.mipmap.first,R.mipmap.second,R.mipmap.thi,R.mipmap.four,R.mipmap.first,R.mipmap.second,R.mipmap.thi,R.mipmap.four,R.mipmap.first};

    ArrayList<dataprovider>arrayList=new ArrayList<dataprovider>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        f_name=getResources().getStringArray(R.array.moviesnames);
        d_name=getResources().getStringArray(R.array.description);
        int i=0;
        for(String name: f_name)
        {
            dataprovider dataprovider=new dataprovider(Img_res[i],name,d_name[i]);
            arrayList.add(dataprovider);
            i++;


        }

        adapter=new RecyclerAdapter(arrayList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
