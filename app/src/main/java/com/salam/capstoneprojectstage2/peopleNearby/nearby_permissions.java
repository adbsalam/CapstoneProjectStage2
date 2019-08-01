package com.salam.capstoneprojectstage2.peopleNearby;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.salam.capstoneprojectstage2.R;

public class nearby_permissions extends AppCompatActivity {

    private Button btngrnt;
    String requestS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_permissions);
        btngrnt = findViewById(R.id.alow_location_btn);
        getSupportActionBar().setTitle(getString(R.string.PERMISSION));


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
                                requestS = request.getStringExtra(getString(R.string.REQUEST));



                                startActivity(new Intent(nearby_permissions.this, nearby_maps.class));
                                finish();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(nearby_permissions.this);
                                    builder.setTitle(getString(R.string.DENIED))
                                            .setMessage(getString(R.string.SETTING_LOC_HELP))
                                            .setNegativeButton(getString(R.string.CANCEL), null)
                                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.setData(Uri.fromParts(getString(R.string.PACKAGE), getPackageName(), null));

                                                }
                                            })
                                            .show();
                                } else {
                                    Toast.makeText(nearby_permissions.this, getString(R.string.PERMISSION_DENIED_ERR), Toast.LENGTH_LONG).show();
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
