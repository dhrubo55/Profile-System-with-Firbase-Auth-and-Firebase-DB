package com.example.dhruboandroid.routemaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

// Activity with nav drawer for the users
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ProfileActivity Atiar= ";



    private GoogleApiClient mGoogleSignInClient;

    private TextView nav_name;
    private TextView nav_email;
    private ImageView imageView;
    public String name;
    public String email;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
//    private ImageView profile_imageView;
//    private Bitmap currentImage;


    //Todo pick image from gallery and place it on the imagview
    //Todo make profile page editable
    //todo saving and fetching data from the firebase database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_email = findViewById(R.id.nav_Email);
        nav_name = findViewById(R.id.nav_Name);


//
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//            } else {
//                name= extras.getString("Name");
//                email = extras.getString("Email");
//                Log.e(TAG,name);
//            }
//        }
        //setSupportActionBar(toolbar);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


//        profile_imageView = findViewById(R.id.imageView);
//
//       profile_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, 1);
//            }
//       });
//
    }
//



    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }
        else if(id==R.id.nav_Name){
            nav_name.setText(name);

        } else if(id==R.id.nav_Email){
            nav_email.setText(email);
        } else if(id==R.id.imageView){

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, RouteInfo.class));

        } else if (id == R.id.nav_manage) {

            startActivity(new Intent(MainActivity.this, AboutUs.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

        } else if (id == R.id.nav_signout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //sign out method
    public void signOut() {
        firebaseAuth.signOut();
        //UserSignOutFunction();
    }

    @Override
    public void onStop() {
        super.onStop();

        // on stop the authentication listener is stopped.
        if (authListener != null) {
            firebaseAuth.removeAuthStateListener(authListener);
        }

//        if (currentImage != null) {
//            currentImage.recycle();
//            currentImage = null;
        System.gc();
    }



    public void UserSignOutFunction() {

        // Sing Out the User.
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                        // Write down your any code here which you want to execute After Sign Out.

                        // Printing Logout toast message on screen.
                        Toast.makeText(MainActivity.this, "Google Logout successful",Toast.LENGTH_LONG).show();


                    }
                });


    }
}


