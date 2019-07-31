package com.salam.capstoneprojectstage2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salam.capstoneprojectstage2.Models.user_details;
import com.salam.capstoneprojectstage2.Registration.Location_Permission;
import com.salam.capstoneprojectstage2.User_UI.User_Home;
import com.salam.capstoneprojectstage2.sign_in.Log_in;

public class MainActivity extends AppCompatActivity {

    Button Register, Sign_in;
    FirebaseUser firebaseUser;
    DatabaseReference dbref;





    @Override
    protected void onStart() {
        super.onStart();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users");


        if (firebaseUser !=null){


            dbref.orderByChild("id").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_details user  = dataSnapshot.getValue(user_details.class);

                    if (user == null){
                        Intent loginbusiness = new Intent(MainActivity.this, Log_in.class);
                        startActivity(loginbusiness);
                        finish();

                    }
                    else {
                        Intent loginauto = new Intent(MainActivity.this, User_Home.class);
                        startActivity(loginauto);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Intent loginauto = new Intent(MainActivity.this, User_Home.class);
                    startActivity(loginauto);
                    finish();

                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Register = findViewById(R.id.btn_register);
        Sign_in = findViewById(R.id.btn_signin);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, Location_Permission.class);
                startActivity(register);

            }
        });


        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Signin = new Intent(MainActivity.this, Log_in.class);
                startActivity(Signin);


            }
        });





    }
}
