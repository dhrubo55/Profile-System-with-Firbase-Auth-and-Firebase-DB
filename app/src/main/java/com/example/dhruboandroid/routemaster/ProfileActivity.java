package com.example.dhruboandroid.routemaster;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button profile_edit;
    private Button profile_save;
    private static final int SELECTED_PIC=1;

    private static final String TAG = "ProfileActivity Atiar= ";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firedbinstance;
    private DatabaseReference firedbReference;

    private EditText profile_name;
    private EditText profile_email;
    private EditText profile_social;
    private EditText profile_group;
    private EditText profile_number;
    private ImageView profile_image;

    private String usrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // initailizing profile attirbutes
        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_social = findViewById(R.id.profile_socialmedia);
        profile_group = findViewById(R.id.profile_group);
        profile_number = findViewById(R.id.profile_number);
        profile_image = findViewById(R.id.profile_imageView);

        profile_edit = findViewById(R.id.profile_edit_button);
        profile_save = findViewById(R.id.profile_save_button);



        //initializing firebaseauth and firebasedatabasee
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            usrID = firebaseAuth.getCurrentUser().getUid();
            Log.e(TAG,usrID);

        }catch (NullPointerException e){
            Toast.makeText(this, "User Id not available", Toast.LENGTH_SHORT).show();
        }
        firedbinstance = FirebaseDatabase.getInstance();
        firedbReference = firedbinstance.getReference("users");

        firedbinstance.getReference("app_title").setValue("RouteMaster");

        readData(usrID);

        profile_name.setEnabled(false);
        profile_email.setEnabled(false);
        profile_social.setEnabled(false);
        profile_group.setEnabled(false);
        profile_number.setEnabled(false);




        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_save.setVisibility(View.VISIBLE);
                profile_edit.setVisibility(View.INVISIBLE);
                profile_email.setEnabled(true);
                profile_social.setEnabled(true);
                profile_group.setEnabled(true);
                profile_number.setEnabled(true);
            }
        });

        profile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = profile_name.getText().toString().trim();
                String email = profile_email.getText().toString().trim();
                String usrid = usrID;
                String social = profile_social.getText().toString().trim();
                String group = profile_group.getText().toString().trim();
                String number = profile_number.getText().toString().trim();


                if (TextUtils.isEmpty(usrid)) {
                    createUser(name, email, usrid, social, group, number);
                } else {
                    updateUser(name, email, usrid, social, group, number);
                }

                profile_edit.setVisibility(View.VISIBLE);
                profile_save.setVisibility(View.INVISIBLE);
                profile_email.setEnabled(false);
                profile_social.setEnabled(false);
                profile_group.setEnabled(false);
                profile_number.setEnabled(false);
            }
        });

        //imageview onClick listener



    }


    public void imageViewClick(View v) {
        Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntent, SELECTED_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SELECTED_PIC:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    Drawable drwable = new BitmapDrawable(getResources(),bitmap);
                    profile_image.setImageDrawable(drwable);
                }
                break;

                default:
                    break;
        }
    }

    private void createUser(String n, String e, String id, String s, String g, String num){
        if(id==null){
            //
        }
        userDataModelClass user = new userDataModelClass(n,e,id,s,g,num);
        firedbReference.child(id).setValue(user);

        addUserChangeLisener(id);
    }

    private void addUserChangeLisener(String i) {
        firedbReference.child(i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDataModelClass user = dataSnapshot.getValue(userDataModelClass.class);

                if (user == null) {
                    Log.e(TAG, "userData is null");
                    return;
                }

                Log.e(TAG, "user Data is changed");

                profile_email.setText(user.user_email);
                profile_name.setText(user.user_name);
                profile_social.setText(user.social_media);
                profile_group.setText(user.group);
                profile_number.setTag(user.number);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "Failed to read user", databaseError.toException());
            }
        });
    }

    private void updateUser(String n, String e, String id, String s, String g, String number){
        if(!TextUtils.isEmpty(n)){

            firedbReference.child(id).child("user_name").setValue(n);

        }
        if(!TextUtils.isEmpty(e)){


            firedbReference.child(id).child("user_email").setValue(e);
        }

        if(!TextUtils.isEmpty(s)){
            firedbReference.child(id).child("social_media").setValue(s);
        }

        if(!TextUtils.isEmpty(g)){
            firedbReference.child(id).child("group").setValue(g);
        }

        if(!TextUtils.isEmpty(number)){
            firedbReference.child(id).child("number").setValue(number);
        }


    }

    private void readData(String userId){

        firedbReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userDataModelClass user   = dataSnapshot.getValue(userDataModelClass.class);

                Log.d(TAG, "Social Media: " + user.social_media +
                        "\n email " + user.user_email);



                profile_name.setText(user.user_name);
                profile_email.setText(user.user_email);
                profile_group.setText(user.group);
                profile_social.setText(user.social_media);
                profile_number.setText(user.number);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        }

}

