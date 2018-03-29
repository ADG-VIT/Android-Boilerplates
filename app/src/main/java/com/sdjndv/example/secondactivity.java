package com.sdjndv.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class secondactivity extends AppCompatActivity {

    private Button tbn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

        tbn2=(Button)findViewById(R.id.btn2);

        tbn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(secondactivity.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
}
