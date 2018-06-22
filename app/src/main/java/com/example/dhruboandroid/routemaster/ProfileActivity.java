package com.example.dhruboandroid.routemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private static final String TAG = "ProfileActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firedbinstance;
    private DatabaseReference firedbReference;

    private EditText profile_name;
    private EditText profile_email;
    private EditText profile_social;
    private EditText profile_group;
    private EditText profile_number;

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


        profile_edit = findViewById(R.id.profile_edit_button);
        profile_save = findViewById(R.id.profile_save_button);

        profile_name.setEnabled(false);
        profile_email.setEnabled(false);
        profile_social.setEnabled(false);
        profile_group.setEnabled(false);
        profile_number.setEnabled(false);

        //initializing firebaseauth and firebasedatabasee
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            usrID = firebaseAuth.getCurrentUser().getUid();
        }catch (NullPointerException e){
            Toast.makeText(this, "User Id not available", Toast.LENGTH_SHORT).show();
        }
        firedbinstance = FirebaseDatabase.getInstance();
        firedbReference = firedbinstance.getReference("users");

        firedbinstance.getReference("app_title").setValue("RouteMaster");


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

}

