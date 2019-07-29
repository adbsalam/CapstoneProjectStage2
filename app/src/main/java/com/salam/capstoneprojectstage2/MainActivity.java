package com.salam.capstoneprojectstage2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.salam.capstoneprojectstage2.Registration.Location_Permission;
import com.salam.capstoneprojectstage2.sign_in.Log_in;

public class MainActivity extends AppCompatActivity {

    Button Register, Sign_in;
    FirebaseUser firebaseUser;
    DatabaseReference dbref;





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
