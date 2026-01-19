package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.Map;

public class usignup extends AppCompatActivity {

    EditText username,password,email,phno;
    AppCompatButton signupbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;
    String Userid;
    //ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usignup);

        username=findViewById(R.id.txtusername);
        password=findViewById(R.id.txtpass);
        email=findViewById(R.id.txtemail);
        phno=findViewById(R.id.txtphno);
        signupbtn=findViewById(R.id.usignupbtn);
        //progressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String mail=email.getText().toString().trim();
                String pno=phno.getText().toString().trim();

                if(TextUtils.isEmpty(mail)&& TextUtils.isEmpty(pass)){
                    email.setError("email is empty");
                    password.setError("password is empty");
                }
                if(pass.length()<6){
                    password.setError("Password should be 6 characters long");
                }

                fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Email already exists, please use different email",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            if (fAuth.getCurrentUser()!=null){
                                Userid=fAuth.getCurrentUser().getUid();
                            }
                        }
                        DocumentReference df=firebaseFirestore.collection("users").document(Userid);
                        userModel model=new userModel(uname,pass,mail,pno,Userid);
                        df.set(model,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"User created successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                /*fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Email already exists. please use different email",Toast.LENGTH_SHORT).show();
                            }else{
                                if(fAuth.getCurrentUser()!=null){
                                    Userid=fAuth.getCurrentUser().getUid();
                                }
                            }
                            DocumentReference df=firebaseFirestore.collection("appUsers").document(Userid);
                            userModel model=new userModel(uname,pass,mail,pno,Userid);
                            df.set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(usignup.this,"User created Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(usignup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });*/
            }
        });
    }
}