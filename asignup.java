package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class asignup extends AppCompatActivity {
    private EditText username,password,email,mobileno,branch;
    private AppCompatButton asignupbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    //private FirebaseAuthUserCollisionException firebaseAuthUserCollisionException;
    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignup);

        username=findViewById(R.id.txtuser);
        password=findViewById(R.id.txtpass);
        email=findViewById(R.id.txtemail);
        mobileno=findViewById(R.id.txtmobno);
        branch=findViewById(R.id.txtbranch);
        asignupbtn=findViewById(R.id.signupbtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        asignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String mail=email.getText().toString().trim();
                String mno=mobileno.getText().toString().trim();
                String bname=branch.getText().toString().trim();

                if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(pass)&&TextUtils.isEmpty(mail)&&TextUtils.isEmpty(mno)&&TextUtils.isEmpty(bname)){
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                if(pass.length()<6){
                    password.setError("Password should be 6 character long");
                }
                firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Email already exists, please use different email",Toast.LENGTH_SHORT).show();
                            }else{
                                if(firebaseAuth.getCurrentUser()!=null){
                                    Userid=firebaseAuth.getCurrentUser().getUid();
                                }
                            }
                            DocumentReference df=firebaseFirestore.collection("admins").document(Userid);
                            adminModel model=new adminModel(name,pass,mail,mno,bname,Userid);
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
                    }
                });

               /*firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Email already exists. please use different email",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(firebaseAuth.getCurrentUser()!=null){
                                    Userid=firebaseAuth.getCurrentUser().getUid();
                                }
                            }
                            DocumentReference df=firebaseFirestore.collection("appAdmins").document(Userid);
                            adminModel model=new adminModel(name,pass,mail,mno,bname,Userid);
                            df.set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(asignup.this,"Admin created Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(asignup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });*/
            }
        });
    }
}