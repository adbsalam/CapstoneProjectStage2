package com.salam.capstoneprojectstage2.User_UI;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.salam.capstoneprojectstage2.Models.user_details;
import com.salam.capstoneprojectstage2.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Edit_profile extends AppCompatActivity {
        ImageView edit_profile_dp;
    private Uri imageUri;
    StorageReference storagereference;
    private static final int IMAGE_REQ = 1;
    private StorageTask uploadTask;
    DatabaseReference reference;
    String pic_ref;
    FirebaseUser firebaseUser;
    TextView fullname_txt;
    TextView company_txt;
    ImageView edit_dp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit_profile_dp = findViewById(R.id.edit_icon);
        fullname_txt = findViewById(R.id.full_name_txt);
        company_txt = findViewById(R.id.comapny_txt);
        edit_dp = findViewById(R.id.edit_dp);
        getSupportActionBar().setTitle(getString(R.string.EDIT_TITLE));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storagereference = FirebaseStorage.getInstance().getReference(getString(R.string.UPLOAD_DB));
        reference = FirebaseDatabase.getInstance().getReference(getString(R.string.USER_ED)).child(firebaseUser.getUid());


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_details user = dataSnapshot.getValue(user_details.class);
                fullname_txt.setText(user.getFirst_name().toString() + user.getLast_name().toString());
                company_txt.setText(user.getCompany_name().toString());
                if (user.getimageURL().equals(getString(R.string.DEFAULT_URL))){
                    edit_dp.setImageResource(android.R.drawable.ic_menu_compass);
                }
                else
                {
                    Picasso.get().load(user.getimageURL()).into(edit_dp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        edit_profile_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic_ref = getString(R.string.IMAGE_URL);
                openimage();

            }
        });

    }



    private void openimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQ);
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentresolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentresolver.getType(uri));


    }


    private byte[] compress(Uri image){
        Uri selectedImage = image;

        InputStream imageStream = null;
        try {
            imageStream = getApplicationContext().getContentResolver().openInputStream(
                    selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
            stream = null;
            return byteArray;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.UPLOADING));
        pd.show();


        if (imageUri != null) {
            final StorageReference filereference = storagereference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            //////
            ////
            // Uri resultUri = imageUri;

            //getting imageUri and store in file. and compress to bitmap





            // compressedImageFile = new Compressor(getContext()).compressToFile(imageUri);
            byte[] data = compress(imageUri);

            //////////
            uploadTask = filereference.putBytes(data);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();


                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();



                        if (pic_ref == getString(R.string.IMAGE_URL)) {
                            reference = FirebaseDatabase.getInstance().getReference(getString(R.string.USER_ED)).child(firebaseUser.getUid());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put(getString(R.string.IMAGE_URL), mUri);
                            reference.updateChildren(map);

                            pd.dismiss();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.FAILED), Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.NULL_IMAGE), Toast.LENGTH_SHORT).show();


        }

    }

    public void onActivityResult(int requestcode, int resultcode, Intent data) {

        super.onActivityResult(requestcode, resultcode, data);

        if (requestcode == IMAGE_REQ && resultcode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();





        }
        if (uploadTask != null && uploadTask.isInProgress()) {
            Toast.makeText(getApplicationContext(), getString(R.string.UPLOADING_MSG), Toast.LENGTH_LONG).show();


        } else {

            uploadImage();
        }


    }





}
