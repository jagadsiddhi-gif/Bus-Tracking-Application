package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DSignin extends AppCompatActivity {
    EditText drivermail,password;
    Button signinbtn;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsignin);
         drivermail=findViewById(R.id.drivermail);
         password=findViewById(R.id.driverpass);
         signinbtn=findViewById(R.id.signinbtn);

         firebaseAuth=FirebaseAuth.getInstance();
         firebaseFirestore=FirebaseFirestore.getInstance();

         signinbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String mail,pass;
                 mail=drivermail.getText().toString();
                 pass=password.getText().toString();

                 if(TextUtils.isEmpty(mail) && TextUtils.isEmpty(pass)){
                     Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                 }else {
                     firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                             Intent intent=new Intent(DSignin.this,driverwithbus.class);
                             startActivity(intent);
                         }
                     });
                 }
             }
         });
    }
}