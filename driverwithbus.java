package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class driverwithbus extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Button updatebn;
    String Latitude,Longitude,userid,addressString,docid;
    TextView getlocation,drivermail,drivername,driverbusid,driverbusno,driverpassword;
    Switch switchbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverwithbus);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        drivername = findViewById(R.id.txtdname);
        drivermail = findViewById(R.id.txtdmail);
        driverpassword = findViewById(R.id.txtdpass);
        driverbusid = findViewById(R.id.txtdbusid);
        driverbusno = findViewById(R.id.txtdbusno);
        switchbtn=findViewById(R.id.switchbtn);
        getlocation=findViewById(R.id.txtlocation);
        updatebn=findViewById(R.id.updatebtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userid = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Driver").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                drivername.setText(documentSnapshot.getString("drivername"));
                drivermail.setText(documentSnapshot.getString("drivermail"));
                driverpassword.setText(documentSnapshot.getString("driverpassword"));
                driverbusid.setText(documentSnapshot.getString("dbusid"));
                driverbusno.setText(documentSnapshot.getString("dbusno"));
            }
        });

        updatebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busid=driverbusid.getText().toString().trim();
                String busno=driverbusno.getText().toString().trim();
                String fulladdress=addressString.toString();

                if(!switchbtn.isChecked()){
                    Toast.makeText(driverwithbus.this, "please switch the location", Toast.LENGTH_SHORT).show();
                }else {
                    if (firebaseAuth.getCurrentUser()!=null){
                        userid=firebaseAuth.getCurrentUser().getUid();
                    }

                    DocumentReference documentReference1=firebaseFirestore.collection("availableinfo").document();
                    availablemodle amodle=new availablemodle(busid,busno,fulladdress,userid,docid);
                    documentReference1.set(amodle,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                if (task.isSuccessful()){
                                    docid=documentReference1.getId();
                                    amodle.setDocid(docid);

                                    documentReference1.set(amodle,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(driverwithbus.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(driverwithbus.this,availablebuses.class);

                                            intent.putExtra("userid",userid);
                                            intent.putExtra("docid",docid);

                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(driverwithbus.this, "Not uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    requestLocation();
                }
            }

            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                CompoundButton.OnCheckedChangeListener.super.toString();
                if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation();
                } else {
                    Toast.makeText(driverwithbus.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
            public void requestLocation() {
                if (ActivityCompat.checkSelfPermission(driverwithbus.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(driverwithbus.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(driverwithbus.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                    return;
                }

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(driverwithbus.this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Latitude=String.valueOf(latitude);
                        Longitude=String.valueOf(longitude);

                        // Get address from latitude and longitude
                        getAddressFromLocation(latitude, longitude);
                    } else {
                        // Handle null location
                        Toast.makeText(driverwithbus.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                        OnGPS();
                    }
                });
            }

            private void getAddressFromLocation(double latitude, double longitude) {
                Geocoder geocoder = new Geocoder(driverwithbus.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        addressString = address.getAddressLine(0); // You can customize the address format as per your requirement
                        getlocation.setText(addressString);
                        getlocation.setSelected(true);
                        //Toast.makeText(this, "Address: " + addressString, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Address not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed to get address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void OnGPS() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(driverwithbus.this);
        builder.setMessage("Enable gps").setCancelable(false).setPositiveButton(
                "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }
        ).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialogg=builder.create();
        dialogg.show();

    }
}