package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        Button button2 = (Button) findViewById(R.id.dsigninbtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    public void openActivity1(){
        Intent intent=new Intent(this,DSignup.class);
        startActivity(intent);
    }
    public void openActivity2(){
        Intent intent=new Intent(this,DSignin.class);
        startActivity(intent);
    }
}