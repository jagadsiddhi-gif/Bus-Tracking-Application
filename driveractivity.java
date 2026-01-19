package com.example.trackingappliaction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class driveractivity extends AppCompatActivity {
    Button addbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driveractivity);

        addbutton=findViewById(R.id.addbtn);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        /*removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2("https://console.firebase.google.com/u/0/project/tracking-appliaction-2/authentication/users");
            }
        });
         */
    }

    private void openActivity1(){
        Intent intent=new Intent(this,DSignup.class);
        startActivity(intent);
    }
    /*private void openActivity2(String s){
        try {
            Uri uri=Uri.parse(s);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"no site found",Toast.LENGTH_SHORT).show();
        }
    }
     */
}