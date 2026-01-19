package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverOrUser extends AppCompatActivity {
    private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_or_user);

        Button button1 = (Button) findViewById(R.id.txtdriver);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        Button button2 = (Button) findViewById(R.id.txtuser);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        Button button3=(Button) findViewById(R.id.txtadmin);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }
    public void openActivity1(){
        Intent intent=new Intent(this,DriverSignUp.class);
        startActivity(intent);
    }
    public void openActivity2(){
        Intent intent=new Intent(this,userSignup.class);
        startActivity(intent);
    }
    public void openActivity3(){
        Intent intent=new Intent(this,adminsignup.class);
        startActivity(intent);
    }
}