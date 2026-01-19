package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class availablebuses extends AppCompatActivity {

    TextView busid,busno,buslocation;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    String userid,docid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availablebuses);

        busid = findViewById(R.id.txtbusid);
        busno = findViewById(R.id.txtbusno);
        buslocation = findViewById(R.id.txtbuslocation);

        firebaseFirestore=FirebaseFirestore.getInstance();

        docid=getIntent().getStringExtra("docid");

            DocumentReference documentReference=firebaseFirestore.collection("availableinfo").document("IU7i79CknjpO9zLGRG90");
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String data1=documentSnapshot.getString("busid");
                        String data2=documentSnapshot.getString("busno");
                        String data3=documentSnapshot.getString("fulladdress");
                        busid.setText(data1);
                        busno.setText(data2);
                        buslocation.setText(data3);
                    }else {
                        Toast.makeText(getApplicationContext()," sorry id:"+docid,Toast.LENGTH_SHORT).show();
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