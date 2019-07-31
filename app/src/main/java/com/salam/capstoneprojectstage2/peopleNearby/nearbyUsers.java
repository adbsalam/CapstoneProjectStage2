package com.salam.capstoneprojectstage2.peopleNearby;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salam.capstoneprojectstage2.Adapter.nearbyAdapter;
import com.salam.capstoneprojectstage2.Models.Location_model;
import com.salam.capstoneprojectstage2.Models.user_details;
import com.salam.capstoneprojectstage2.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class nearbyUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    String userid = "";
    String log = "asdasd";
    String newone = "";
    String Latitudecustomer;
    String distanceint;
    Integer i = 1;
    TextView usertxtlist;
    String Longitudecustomer;
    Location newlocation = new Location("");
    Double distance2 = 6714242.0;

    List<user_details> users = new ArrayList<>();
    List<Location_model> locations = new ArrayList<>();



        String getLati, getLongi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_users);
getSupportActionBar().setTitle("Professionals");
      Intent i = getIntent();
      getLati = i.getStringExtra("lati");
      getLongi = i.getStringExtra("longi");





        // Inflate the layout for this fragment

        recyclerView = findViewById(R.id.nearby_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        readUser();




    }


    private void readUser() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference locationref = FirebaseDatabase.getInstance().getReference("Location");

        locationref.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String servicelongitude;
                String Servicelatitude;
                Location anotherlocation = new Location("");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Location_model locs = snapshot.getValue(Location_model.class);


                    if (locs.getLatitude() != null){
                        anotherlocation.setLongitude(Double.parseDouble(locs.getLongitude()));
                        anotherlocation.setLatitude(Double.parseDouble(locs.getLatitude()));
                    }

                    Log.d("location", locs.getId());


                    float distance = newlocation.distanceTo(anotherlocation);
                    userid = locs.getId();
                    Log.d("now", String.valueOf(distance2));
                    Log.d("now", String.valueOf(distance));
                    if (distance < distance2){
                        locations.add(locs);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.orderByChild("id").equalTo(locs.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                    user_details user = snapshot.getValue(user_details.class);
                                    if (!user.getId().equals(firebaseUser.getUid())){
                                        if (user.getId().equals(locs.getId())) {

                                            users.add(user);

                                        }
                                    }

                                }

                                HashSet hs = new HashSet();
                                hs.addAll(users);
                                users.clear();
                                users.addAll(hs);
                                Log.d("data", users.toString());

                                nearbyAdapter userAdapter = new nearbyAdapter(nearbyUsers.this ,users);
                                recyclerView.setAdapter(userAdapter);

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }







}
