package com.salam.capstoneprojectstage2.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salam.capstoneprojectstage2.MainActivity;
import com.salam.capstoneprojectstage2.R;

import java.util.HashMap;

public class RegisterPhone extends AppCompatActivity {
    Button next;

    EditText first_name, last_name,  companyName, email, password;
    String Lati, Longi, adrr, first_txt, last_txt, companyname_txt, email_txt, password_txt;
    String aboutu = "Add a few lines about yourself to describe you";
    //firebase Initiate
    FirebaseAuth auth;
    DatabaseReference dbref;
    DatabaseReference refprofile;
    DatabaseReference ratingdatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        Intent loc = getIntent();
        Lati = loc.getStringExtra("lati");
        Longi = loc.getStringExtra("longi");
        adrr = loc.getStringExtra("adr");


        email = findViewById(R.id.phone_txt);
        first_name = findViewById(R.id.firstname_txt);
        last_name= findViewById(R.id.lastname_txt);
        companyName = findViewById(R.id.company_txt);
        next = findViewById(R.id.next_btn);
        password = findViewById(R.id.password_txt);

        auth = FirebaseAuth.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               first_txt = first_name.getText().toString();
               last_txt = last_name.getText().toString();
              email_txt = email.getText().toString();
               companyname_txt = companyName.getText().toString();
               password_txt = password.getText().toString();


                if (first_txt.isEmpty()){
                    first_name.setError("First Name is required");
                    first_name.requestFocus();
                    return;

                }
                if (last_txt.isEmpty()){
                    last_name.setError("Last Name is Required");
                    last_name.requestFocus();
                    return;

                }
                if (companyname_txt.isEmpty()){
                    companyName.setError("Company Name is Required");
                    companyName.requestFocus();
                    return;

                }
                if (email_txt.isEmpty()){
                    email.setError("Phone Number is required");
                    email.requestFocus();
                    return;

                }
                if (password_txt.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;

                }

                register();



            }
        });

    }




    private void register() {
        //authentication for new user
        auth.createUserWithEmailAndPassword(email_txt, password_txt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            final String userid = firebaseUser.getUid();

                            dbref = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            // refprofile = FirebaseDatabase.getInstance().getReference("profile").child(userid);


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("First_name", first_txt);
                            hashMap.put("Last_name", last_txt);
                            hashMap.put("Company_name", companyname_txt);
                            hashMap.put("Email", email_txt);
                            hashMap.put("Adress", adrr);
                            hashMap.put("About_you", aboutu);


                            dbref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){


                                        ratingdatabase = FirebaseDatabase.getInstance().getReference("Location").child(userid);

                                        HashMap<String, String> hashrate = new HashMap<>();
                                        hashrate.put("id", userid);
                                        hashrate.put("Latitude", Lati);
                                        hashrate.put("Longitude", Longi);

                                        ratingdatabase.setValue(hashrate);






                                    }
                                    else {
                                        Toast.makeText(RegisterPhone.this, "done", Toast.LENGTH_SHORT).show();

                                    }

                                    sendVerificationEmail();
                                }
                            });

                        }
                    }
                });

    }



    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterPhone.this, MainActivity.class));
                            finish();

                            Toast.makeText(RegisterPhone.this, "A verification Email is sent to your email address", Toast.LENGTH_LONG).show();





                        }
                        else
                        {

                            Toast.makeText(RegisterPhone.this, "A casdasdasd Email is sent to your email address", Toast.LENGTH_SHORT).show();



                        }
                    }
                });
    }




}
