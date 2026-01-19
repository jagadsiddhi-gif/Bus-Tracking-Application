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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class DSignup extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String Userid,docid;
    EditText dname,dmail,busid,busno,dpassword;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsignup);

        dname=findViewById(R.id.txtdname);
        dmail=findViewById(R.id.txtdmail);
        busid=findViewById(R.id.txtbusid);
        busno=findViewById(R.id.txtbusno);
        dpassword=findViewById(R.id.txtpass);
        signupbtn=findViewById(R.id.adddriverbtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driverusername,drivermail,driverbusid,driverbusno,driverpassword;

                driverusername=dname.getText().toString();
                drivermail=dmail.getText().toString();
                driverbusid=busid.getText().toString();
                driverbusno=busno.getText().toString();
                driverpassword=dpassword.getText().toString().trim();

                if(TextUtils.isEmpty(driverusername) && TextUtils.isEmpty(drivermail) && TextUtils.isEmpty(driverbusid) && TextUtils.isEmpty(driverbusno) && TextUtils.isEmpty(driverpassword)){
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(drivermail,driverpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Email already exists, please use different email",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            if (firebaseAuth.getCurrentUser()!=null){
                                Userid=firebaseAuth.getCurrentUser().getUid();
                            }
                        }
                        DocumentReference df=firebaseFirestore.collection("Driver").document(Userid);
                        driverModule module=new driverModule(driverusername,drivermail,driverbusid,driverbusno,driverpassword,Userid);
                        df.set(module, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                        df.set(module,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                }
                            }
                        });
                    }
                });
            }
        });

    }
}