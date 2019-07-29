package com.salam.capstoneprojectstage2.sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.salam.capstoneprojectstage2.R;
import com.salam.capstoneprojectstage2.User_UI.User_Home;

import java.util.Objects;

public class Log_in extends AppCompatActivity {

    EditText email;
    EditText password;
    Button logincus;
    FirebaseAuth auth;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        final FirebaseUser firebaseUser;
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailTxtM);
        password = findViewById(R.id.passwordTxtM);
        logincus = findViewById(R.id.LogCustomerbtn);
        background= findViewById(R.id.background);

       // final Animation animFadeIn4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        //background.startAnimation(animFadeIn4);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        logincus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    email.setError("Email and Password Cannot be empty");


                    email.requestFocus();
                    return;



                }
                if (txt_password.length() < 5){

                    password.setError("Password Should be atleast 8 Characters");
                }
                else {

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (auth.getCurrentUser() == null){
                                        Toast.makeText(Log_in.this, "Please Make Sure You have Verified the email before Logging in.", Toast.LENGTH_LONG).show();


                                    }

                                    else if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {


                                        if (task.isSuccessful()) {


                                            Intent loginact = new Intent(Log_in.this, User_Home.class);
                                            loginact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(loginact);
                                        }


                                        else {
                                            Toast.makeText(Log_in.this, "Authentification failed", Toast.LENGTH_LONG).show();
                                        }


                                    }






                                }
                            });


                }
            }
        });


    }



}
