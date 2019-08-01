package com.salam.capstoneprojectstage2.peopleNearby;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
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
getSupportActionBar().setTitle(getString(R.string.PROFESSIONALS));
      Intent i = getIntent();
      getLati = i.getStringExtra(getString(R.string.LATI));
      getLongi = i.getStringExtra(getString(R.string.LONGI));

        // Inflate the layout for this fragment
        recyclerView = findViewById(R.id.nearby_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new newUser().execute("");
    }


    private void readUser() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference locationref = FirebaseDatabase.getInstance().getReference(getString(R.string.LOC));

        locationref.orderByChild(getString(R.string.ID_U)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Location anotherlocation = new Location("");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Location_model locs = snapshot.getValue(Location_model.class);
                    if (locs.getLatitude() != null){
                        anotherlocation.setLongitude(Double.parseDouble(locs.getLongitude()));
                        anotherlocation.setLatitude(Double.parseDouble(locs.getLatitude()));
                    }
                    float distance = newlocation.distanceTo(anotherlocation);
                    userid = locs.getId();
                    if (distance < distance2){
                        locations.add(locs);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getString(R.string.USERS));
                        reference.orderByChild(getString(R.string.ID_U)).equalTo(locs.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
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




    private class newUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            readUser();
            return "done";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
