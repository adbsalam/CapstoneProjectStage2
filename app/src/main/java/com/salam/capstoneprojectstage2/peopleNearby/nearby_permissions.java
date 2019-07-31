package com.salam.capstoneprojectstage2.peopleNearby;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.salam.capstoneprojectstage2.R;
import com.salam.capstoneprojectstage2.Registration.Location_Permission;
import com.salam.capstoneprojectstage2.Registration.RegistrationMaps;

public class nearby_permissions extends AppCompatActivity {

    private Button btngrnt;
    String requestS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_permissions);
        btngrnt = findViewById(R.id.alow_location_btn);
        getSupportActionBar().setTitle("Permission");


        if (ContextCompat.checkSelfPermission(nearby_permissions.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(nearby_permissions.this, nearby_maps.class));
            finish();
            return;
        }

        btngrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(nearby_permissions.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent request = getIntent();
                                requestS = request.getStringExtra("request");



                                startActivity(new Intent(nearby_permissions.this, nearby_maps.class));
                                finish();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(nearby_permissions.this);
                                    builder.setTitle("Permission Denied")
                                            .setMessage("Permission is denied, Please go to settings>>app>>permissions, and allow app to use location.")
                                            .setNegativeButton("Cancel", null)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.setData(Uri.fromParts("package", getPackageName(), null));

                                                }
                                            })
                                            .show();
                                } else {
                                    Toast.makeText(nearby_permissions.this, "Permission Denied", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .check();
            }
        });

    }



}
