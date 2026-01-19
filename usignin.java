package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class usignin extends AppCompatActivity {
    private EditText email,password;
    private Button usigninbtn;

    private FirebaseAuth firebaseAuth;
    private TextView forgotpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usignin);

        email=findViewById(R.id.txtmail);
        password=findViewById(R.id.txtpass);
        usigninbtn=findViewById(R.id.usigninbtn);
        forgotpassword=findViewById(R.id.forgetpass);

        firebaseAuth=FirebaseAuth.getInstance();

        usigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinuser();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotActivity();
            }
        });
    }
    private void forgotActivity(){
        Intent intent=new Intent(this,resetpassword.class);
        startActivity(intent);
    }
    private void signinuser(){
        String mail=email.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if(TextUtils.isEmpty(mail)&&TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(),"Please enter username and password",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                       // startActivity(intent);
                        Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(usignin.this,availablebuses.class);
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}