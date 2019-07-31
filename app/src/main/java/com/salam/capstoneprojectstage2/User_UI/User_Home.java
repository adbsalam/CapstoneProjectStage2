package com.salam.capstoneprojectstage2.User_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salam.capstoneprojectstage2.Bookmarks.bookmarks_act;
import com.salam.capstoneprojectstage2.MainActivity;
import com.salam.capstoneprojectstage2.Models.user_details;
import com.salam.capstoneprojectstage2.R;
import com.salam.capstoneprojectstage2.Search_query_UI.start_search_ui;
import com.salam.capstoneprojectstage2.peopleNearby.nearby_permissions;

public class User_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextView fullname_nav, email_nav;
    ImageView profile_dp;
    CardView searchcard, nearbycard, favcard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        fullname_nav = headerView.findViewById(R.id.nav_full_name);
        email_nav = headerView.findViewById(R.id.nav_email);
        profile_dp = headerView.findViewById(R.id.imageView);
        nearbycard = findViewById(R.id.nearby_card);
        favcard = findViewById(R.id.Bookmarks_card);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        searchcard = findViewById(R.id.search_card);


        searchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent searchIntent = new Intent(User_Home.this, start_search_ui.class);
                startActivity(searchIntent);


            }
        });

        nearbycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(User_Home.this, nearby_permissions.class);
                startActivity(searchIntent);
            }
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_details user = dataSnapshot.getValue(user_details.class);
                fullname_nav.setText(user.getFirst_name() );

                if (user.getimageURL().equals("default_URL")){
                    profile_dp.setImageResource(android.R.drawable.ic_menu_compass);
                }
                else
                {
                    //Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        favcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintnet = new Intent (User_Home.this, bookmarks_act.class);
                startActivity(newintnet);

            }
        });




    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Log_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(User_Home.this, MainActivity.class));
            finish();
            Toast.makeText(User_Home.this, "Sign Out", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(User_Home.this, MainActivity.class));
            finish();
            Toast.makeText(User_Home.this, "Sign Out", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
