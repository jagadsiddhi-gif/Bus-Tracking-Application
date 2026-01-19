package com.example.trackingappliaction2;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class newdriverwithactivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button updatebn;
    String Latitude, Longitude, userid;
    TextView getlocation, drivermail, drivername, driverbusid, driverbusno, driverpassword;
    Button updatebtn;
    Switch switchbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdriverwithactivity);

        getlocation = findViewById(R.id.txtlocation);
        drivername = findViewById(R.id.txtdname);
        drivermail = findViewById(R.id.txtdmail);
        driverbusid = findViewById(R.id.txtdbusid);
        driverbusno = findViewById(R.id.txtdbusno);
        driverpassword = findViewById(R.id.txtdpass);
        updatebn = findViewById(R.id.updatebtn);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            userid = currentUser.getUid();
        } else {
            Toast.makeText(getApplicationContext(), "user is not logged in", Toast.LENGTH_SHORT).show();
        }

        firebaseFirestore.collection("Driver").whereEqualTo("userid", userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                StringBuilder stringBuilder = new StringBuilder();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String dbusid = documentSnapshot.getString("dbusid");
                    String dbusno = documentSnapshot.getString("drivermail");
                    String dmail = documentSnapshot.getString("drivermail");
                    String dname = documentSnapshot.getString("drivername");
                    String dpassword = documentSnapshot.getString("driverpassword");

                    stringBuilder.append("Driver name: ").append(dname).append("\n");
                    stringBuilder.append("Driver mail: ").append(dmail).append("\n");
                    stringBuilder.append("Driver pass: ").append(dpassword).append("\n");
                    stringBuilder.append("Bus id: ").append(dbusid).append("\n");
                    stringBuilder.append("Bus number: ").append(dbusno).append("\n");
                    stringBuilder.append("\n");


                    drivername.setText(dname);
                    drivermail.setText(dmail);
                    driverpassword.setText(dpassword);
                    driverbusid.setText(dbusid);
                    driverbusno.setText(dbusno);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to get data", Toast.LENGTH_SHORT).show();
            }
        });

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    requestLocation();
                }
            }

            public void requestLocation() {
                if (ActivityCompat.checkSelfPermission(newdriverwithactivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(newdriverwithactivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(newdriverwithactivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                    return;
                }

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(newdriverwithactivity.this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Latitude = String.valueOf(latitude);
                        Longitude = String.valueOf(longitude);

                        // Get address from latitude and longitude
                        getAddressFromLocation(latitude, longitude);
                    } else {
                        // Handle null location
                        Toast.makeText(newdriverwithactivity.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                        OnGPS();
                    }
                });
            }

            private void getAddressFromLocation(double latitude, double longitude) {
                Geocoder geocoder = new Geocoder(newdriverwithactivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressString = address.getAddressLine(0); // You can customize the address format as per your requirement
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

        updatebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(newdriverwithactivity.this);
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

        final AlertDialog dialogg = builder.create();
        dialogg.show();
    }
}
