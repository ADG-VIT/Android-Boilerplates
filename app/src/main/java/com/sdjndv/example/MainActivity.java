package com.sdjndv.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt=(TextView)findViewById(R.id.vidit);
        button=(Button)findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt.setText("naman");
                Intent i=new Intent(MainActivity.this,secondactivity.class);
                startActivity(i);


            }
        });

    }



}
