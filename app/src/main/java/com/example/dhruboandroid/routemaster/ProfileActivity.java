package com.example.dhruboandroid.routemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    private Button profile_edit;
    private Button profile_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         profile_edit = findViewById(R.id.profile_edit_button);
         profile_save = findViewById(R.id.profile_save_button);

         profile_edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 profile_save.setVisibility(View.VISIBLE);

             }
         });
    }
}
