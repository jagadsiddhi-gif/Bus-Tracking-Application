package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity {
    private EditText regmail;
    private Button resetbutton;
    private FirebaseAuth firebaseAuth;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        regmail=findViewById(R.id.txtregmail);
        resetbutton=findViewById(R.id.resetbtn);
        firebaseAuth=FirebaseAuth.getInstance();

        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=regmail.getText().toString().trim();

                if(!TextUtils.isEmpty(mail)){
                    resetpass();
                }else{
                    Toast.makeText(resetpassword.this,"Email cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void resetpass(){
        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(resetpassword.this,"Reset password link sent, check your registered email",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(resetpassword.this,"Unable to find email",Toast.LENGTH_SHORT).show();
            }
        });

    }

}