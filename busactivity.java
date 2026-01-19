package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class busactivity extends AppCompatActivity {
    Button addbusbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busactivity);

        addbusbtn=findViewById(R.id.addbtn);

        addbusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(busactivity.this, Addbusnew.class);
                startActivity(intent);
            }
        });
    }
}