package com.example.dhruboandroid.routemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    private Button profile_edit;
    private Button profile_save;

    private EditText profile_email;
    private EditText profile_social;
    private EditText profile_group;
    private EditText profile_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_email = findViewById(R.id.profile_email);
        profile_social = findViewById(R.id.profile_socialmedia);
        profile_group = findViewById(R.id.profile_group);
        profile_number = findViewById(R.id.profile_number);


         profile_edit = findViewById(R.id.profile_edit_button);
         profile_save = findViewById(R.id.profile_save_button);

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
                 profile_edit.setVisibility(View.VISIBLE);
                 profile_save.setVisibility(View.INVISIBLE);
                 profile_email.setEnabled(false);
                 profile_social.setEnabled(false);
                 profile_group.setEnabled(false);
                 profile_number.setEnabled(false);
             }
         });
    }
}
