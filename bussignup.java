package com.example.trackingappliaction2;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;

import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class bussignup extends AppCompatActivity {
    private Switch sbtn;
    private LocationManager locationManager;
    private EditText busno, busname, drivermail;
    private TextView getlocation;
    private Button addbusbtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String Userid, Docid,carLatitude,carLongitude;
    private static final int REQUEST_LOCATION = 1;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussignup);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        busno = findViewById(R.id.busno);
        busname = findViewById(R.id.busname);
        drivermail = findViewById(R.id.drivermail);
        addbusbtn = findViewById(R.id.addbusbtn);
        getlocation = findViewById(R.id.txtlocation);

        sbtn = findViewById(R.id.switchbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        addbusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = busno.getText().toString().trim();
                String name = busname.getText().toString().trim();
                String dmail = drivermail.getText().toString().trim();

                if (TextUtils.isEmpty(no) && TextUtils.isEmpty(name) && TextUtils.isEmpty(dmail)) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(firebaseAuth.getCurrentUser() != null) {
                    Userid = firebaseAuth.getCurrentUser().getUid();
                }
                DocumentReference df = firebaseFirestore.collection("businfomartion").document();
                    busmodel busmodel = new busmodel(no, name, dmail, carLongitude, carLatitude, Docid, Userid);
                df.set(busmodel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (task.isSuccessful()) {
                                Docid = df.getId();
                                busmodel.setCustid(Docid);

                                df.set(busmodel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                });
            }
        }
        });
        sbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                    Toast.makeText(bussignup.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
            public void requestLocation() {
                if (ActivityCompat.checkSelfPermission(bussignup.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(bussignup.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(bussignup.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                    return;
                }

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(bussignup.this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        carLatitude=String.valueOf(latitude);
                        carLongitude=String.valueOf(longitude);

                        // Get address from latitude and longitude
                        getAddressFromLocation(latitude, longitude);
                    } else {
                        // Handle null location
                        Toast.makeText(bussignup.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                        OnGPS();
                    }
                });
            }

            private void getAddressFromLocation(double latitude, double longitude) {
                Geocoder geocoder = new Geocoder(bussignup.this, Locale.getDefault());
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
    }
    private void OnGPS() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(bussignup.this);
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
