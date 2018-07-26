package com.example.dhruboandroid.routemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactUs extends AppCompatActivity {


    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText comments;
    private Button submit;



    private static String TAG = "ContactActivity Dhrubo: ";
    private String id;
    private FirebaseAuth firebaseAuth1;
    private FirebaseDatabase firedbinstance;
    private DatabaseReference firedbReference;
    private DatabaseReference firedbReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        comments = findViewById(R.id.comment);
        submit = findViewById(R.id.button);


        firebaseAuth1 = FirebaseAuth.getInstance();
        id = firebaseAuth1.getCurrentUser().getUid();


        firedbinstance = FirebaseDatabase.getInstance();
        firedbReference = firedbinstance.getReference("users");
        firedbReference1 = firedbinstance.getReference("comment");
        Log.d(TAG,firedbReference1.toString());
        //getFiredbReference = firedbinstance.getReference("Contact Info");
        //firedbinstance.getReference("app_title").setValue("RouteMaster");

        name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);


        firedbReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDataModelClass user = dataSnapshot.getValue(userDataModelClass.class);
                // fetching all the data
                // from firebase servers and placing them in UI
                if (user != null) {
                    Log.d(TAG, "Social Media: " + user.social_media +
                            "\n email " + user.user_email);

                    name.setText(user.user_name);
                    phone.setText(user.number);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        email.setText(firebaseAuth1.getCurrentUser().getEmail());


        if (comments.getText().toString().isEmpty()) {

        } else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String n = name.getText().toString().trim();
                    String p = phone.getText().toString().trim();
                    String c = comments.getText().toString().trim();
                    String e = firebaseAuth1.getCurrentUser().getEmail();
                    boolean flag = !c.isEmpty();
                    createUser(c, id, flag);
                }
            });

        }
    }








    // create user method for creating new user in the app and Firebase
    private void createUser(String c, String id, boolean flag){

        // initializing the data model class of the userDataModel
        userContactsInformation user = new userContactsInformation(c,id, flag);
        firedbReference1.setValue(user);
        Log.d(TAG,user.toString());
        addUserChangeLisener(id);
    }

    // user change listener for updating user information in the current user  (Without Login Out)
    private void addUserChangeLisener(String i) {
        firedbReference1.child(i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userContactsInformation user = dataSnapshot.getValue(userContactsInformation.class);

                if (user == null) {
                    Log.e(TAG, "userData is null");
                    return;
                }

                Log.e(TAG, "user Data is changed");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read user", databaseError.toException());
            }
        });
    }

    //update user information by calling this method.

    private void updateUser(String n, String e, String p, String c, String id){
        if(!TextUtils.isEmpty(n)){
            firedbReference.child(id).child("name").setValue(n);
        }
        if(!TextUtils.isEmpty(e)){
            firedbReference.child(id).child("email").setValue(e);
        }

        if(!TextUtils.isEmpty(p)){
            firedbReference.child(id).child("phone").setValue(p);
        }

        if(!TextUtils.isEmpty(c)){
            firedbReference.child(id).child("comments").setValue(c);
        }


    }
}
