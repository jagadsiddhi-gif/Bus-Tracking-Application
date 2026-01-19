package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userSignup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        Button button1 = (Button) findViewById(R.id.usignupbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
        Button button2 = (Button) findViewById(R.id.usigninbtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    public void openActivity1(){
        Intent intent=new Intent(this,usignup.class);
        startActivity(intent);
    }
    public void openActivity2(){
        Intent intent=new Intent(this,usignin.class);
        startActivity(intent);
    }
}