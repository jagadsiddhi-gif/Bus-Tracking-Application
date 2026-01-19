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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class Addbusnew extends AppCompatActivity {

    EditText busid,busnumber;
    Button addbtn;

    String userid,docid,latitude,longitude;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbusnew);

        busid=findViewById(R.id.txtbusid);
        busnumber=findViewById(R.id.txtbusno);
        addbtn=findViewById(R.id.addbtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=busid.getText().toString().trim();
                String number= busnumber.getText().toString().trim();

                if(TextUtils.isEmpty(id)&&TextUtils.isEmpty(number)){
                    Toast.makeText(getApplicationContext(),"please fill all the details",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(firebaseAuth.getCurrentUser() != null){
                        userid=firebaseAuth.getCurrentUser().getUid();
                    }
                    DocumentReference df=firebaseFirestore.collection("new_businformaiton").document();
                    newbusmodel newbusmodel=new newbusmodel(id,number,userid,latitude,longitude,docid);
                    df.set(newbusmodel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"uploaded successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Addbusnew.this,busactivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}