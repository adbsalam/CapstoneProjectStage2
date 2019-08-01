package com.salam.capstoneprojectstage2.Registration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    String aboutu = getString(R.string.FEW_THINGS);
    //firebase Initiate
    FirebaseAuth auth;
    DatabaseReference dbref;
    DatabaseReference refprofile;
    DatabaseReference ratingdatabase;
    String imageURL = getString(R.string.DEFAULT_URL_R);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        Intent loc = getIntent();
        Lati = loc.getStringExtra(getString(R.string.LATI_R));
        Longi = loc.getStringExtra(getString(R.string.LONGI_R));
        adrr = loc.getStringExtra(getString(R.string.ADR_R));
        getSupportActionBar().setTitle(getString(R.string.REGISTER_R));
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
                    first_name.setError(getString(R.string.F_NAME_ERR));
                    first_name.requestFocus();
                    return;
                }
                if (last_txt.isEmpty()){
                    last_name.setError(getString(R.string.LAST_N_ERR));
                    last_name.requestFocus();
                    return;
                }
                if (companyname_txt.isEmpty()){
                    companyName.setError(getString(R.string.CO_N_ERR));
                    companyName.requestFocus();
                    return;
                }
                if (email_txt.isEmpty()){
                    email.setError(getString(R.string.PHONE_ERR));
                    email.requestFocus();
                    return;
                }
                if (password_txt.isEmpty()){
                    password.setError(getString(R.string.PASS_ERR));
                    password.requestFocus();
                    return;
                }
                new registernew().execute("");
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
                            dbref = FirebaseDatabase.getInstance().getReference(getString(R.string.USERS_DB)).child(userid);
                            // refprofile = FirebaseDatabase.getInstance().getReference("profile").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(getString(R.string.ID_U), userid);
                            hashMap.put(getString(R.string.F_NAME_RR), first_txt);
                            hashMap.put(getString(R.string.LAST_RR), last_txt);
                            hashMap.put(getString(R.string.COMPANY_N_R), companyname_txt);
                            hashMap.put(getString(R.string.EMAIL_R), email_txt);
                            hashMap.put(getString(R.string.ADRRESS_R), adrr);
                            hashMap.put(getString(R.string.ABOUT_LBL), aboutu);
                            hashMap.put(getString(R.string.IMAGE_URL_R), imageURL);
                            dbref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        ratingdatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.LOCATION_R_DB)).child(userid);
                                        HashMap<String, String> hashrate = new HashMap<>();
                                        hashrate.put(getString(R.string.ID_U), userid);
                                        hashrate.put(getString(R.string.LATITUDE_F), Lati);
                                        hashrate.put(getString(R.string.LONGITUDE_F), Longi);
                                        ratingdatabase.setValue(hashrate);
                                    }
                                    else {
                                        Toast.makeText(RegisterPhone.this, getString(R.string.DONE), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterPhone.this, getString(R.string.VERIFY_SENT), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterPhone.this, getString(R.string.VERIFY_ERRR), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private class registernew extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            register();
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
