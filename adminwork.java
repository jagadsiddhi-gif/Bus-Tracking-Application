package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminwork extends AppCompatActivity {
    Button driverbtn,busbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminwork);

        driverbtn=findViewById(R.id.btndriver);
        busbtn=findViewById(R.id.btnbus);

        driverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        busbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminwork.this, busactivity.class);
                startActivity(intent);
            }
        });
    }
    public void openActivity1(){
        Intent intent=new Intent(this,driveractivity.class);
        startActivity(intent);
    }
}