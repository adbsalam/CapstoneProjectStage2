package com.salam.capstoneprojectstage2.peopleNearby;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.salam.capstoneprojectstage2.R;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class nearby_maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    EditText cusadr;
    String latitudeput = "";
    String longiput = "";
    public static final String EXTRA_LAT= "";
    public static final String EXTRA_LON = "";
    RippleBackground rippleBackground;
    private Location mLastknownlocation;
    private LocationCallback locationCallback;
    private View mapView;
    private Button btnFind;
    private final float DEFAULT_ZOOM = 18;
    String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_maps);
        getSupportActionBar().setTitle(getString(R.string.LOCATION));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(nearby_maps.this);
        cusadr = findViewById(R.id.currentadress_service);
        rippleBackground = findViewById(R.id.ripple_poscus);


    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if(mapView != null && mapView.findViewById(Integer.parseInt("1")) != null){

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,40,500);

        }

        LocationRequest locationRequest =  LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(nearby_maps.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(nearby_maps.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(nearby_maps.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException){
                    ResolvableApiException resolvbale = (ResolvableApiException) e;

                    try{
                        resolvbale.startResolutionForResult(nearby_maps.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 51){
            if(requestCode == RESULT_OK){
                getDeviceLocation();
            }

        }

    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(){
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){
                    mLastknownlocation = task.getResult();
                    if (mLastknownlocation != null){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastknownlocation.getLatitude(), mLastknownlocation.getLongitude()), DEFAULT_ZOOM));
                        LatLng cordinates = new LatLng(mLastknownlocation.getLatitude(), mLastknownlocation.getLongitude());
                        String mycity = getCityName(cordinates);

                        LatLng currentlocation = mMap.getCameraPosition().target;
                        rippleBackground.startRippleAnimation();


                    }else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(1000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult ==null){

                                    return;
                                }
                                mLastknownlocation = locationResult.getLastLocation();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastknownlocation.getLongitude(), mLastknownlocation.getLatitude()), DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                            }
                        };
                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, null);
                    }
                }else {

                    Toast.makeText(nearby_maps.this, getString(R.string.UNABLE_ERROR), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getCityName(LatLng cordinates) {
        String mycity = "";
        Geocoder geocoder = new Geocoder(nearby_maps.this, Locale.getDefault());

        try{
            List<Address> addresses = geocoder.getFromLocation(cordinates.latitude, cordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            longiput = String.valueOf(cordinates.longitude);
            latitudeput = String.valueOf(cordinates.latitude);

            mycity = addresses.get(0).getAddressLine(0);

            cusadr.setText(mycity);
            adress = mycity;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mycity;

    }


    public void findaddress(View view) {

        String adrtxt = cusadr.getText().toString();
        Intent startregisterloc = new Intent(nearby_maps.this, nearbyUsers.class);
        startregisterloc.putExtra("lati", latitudeput);
        startregisterloc.putExtra("longi", longiput);
        startregisterloc.putExtra("adr", adrtxt);
        startActivity(startregisterloc);
        finish();


    }

}
