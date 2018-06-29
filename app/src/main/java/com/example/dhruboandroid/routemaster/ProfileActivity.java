package com.example.dhruboandroid.routemaster;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
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

import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Bitmap bitmap;

    private static Context mContext;
    private String usrID;


    public static Context getContext(){
        return mContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//      Declaring custom context
        mContext = getApplicationContext();
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
            Log.e(TAG, usrID);

            // readData(usrID);
        } catch (NullPointerException e) {
            Toast.makeText(this, "User Id not available", Toast.LENGTH_SHORT).show();
        }

        // Firebase database initialized
        firedbinstance = FirebaseDatabase.getInstance();
        firedbReference = firedbinstance.getReference("users");

        firedbinstance.getReference("app_title").setValue("RouteMaster");


        try {
            readData(usrID);
        } catch (NullPointerException e) {

        }


        // Setting all the GUI properties enabled to false
            profile_name.setEnabled(false);
            profile_email.setEnabled(false);
            profile_social.setEnabled(false);
            profile_group.setEnabled(false);
            profile_number.setEnabled(false);
            profile_image.setImageResource(R.drawable.ic_cameraa);

       // event handling of button: edit
            profile_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // enabling the GUI properties
                    profile_save.setVisibility(View.VISIBLE);
                    profile_edit.setVisibility(View.INVISIBLE);
                    profile_email.setEnabled(true);
                    profile_social.setEnabled(true);
                    profile_group.setEnabled(true);
                    profile_number.setEnabled(true);
                    profile_name.setEnabled(true);
                }
            });


        // event handling of button: save
            profile_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = profile_name.getText().toString().trim();
                    String email = profile_email.getText().toString().trim();
                    String usrid = usrID;
                    String social = profile_social.getText().toString().trim();
                    String group = profile_group.getText().toString().trim();
                    String number = profile_number.getText().toString().trim();

        // calling create user if the user have completed the registration process else update user information
                    if (TextUtils.isEmpty(usrid)) {
                        createUser(name, email, usrid, social, group, number);
                    } else {
                        updateUser(name, email, usrid, social, group, number);
                    }
        // save buttion
                    profile_edit.setVisibility(View.VISIBLE);
                    profile_save.setVisibility(View.INVISIBLE);
                    profile_email.setEnabled(false);
                    profile_social.setEnabled(false);
                    profile_group.setEnabled(false);
                    profile_number.setEnabled(false);
                    profile_name.setEnabled(false);
                }
            });

            //imageview onClick listener


        }


   // clickable imageview for taking pictures from the gallery and  setting it on the image view
    public void imageViewClick(View v) {
        Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntent, SELECTED_PIC);
    }
    // Image view gallery pic choosing
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECTED_PIC && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        switch(requestCode){
//            case SELECTED_PIC:
//                if(resultCode == RESULT_OK){
//                    Uri uri = data.getData();
//                    String[] projection = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
//                    cursor.moveToFirst();
//                    int size = projection.length;
//                    Log.e(TAG,size+"");
//                    int columnIndex1 = cursor.getColumnIndex(projection[0]);
//                    String filePath = cursor.getString(columnIndex1);
//
//
//                    cursor.close();
//
//                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                    Drawable drwable = new BitmapDrawable(getResources(),bitmap);
//                    profile_image.setImageDrawable(drwable);


                   // uploadImg(profile_name.getText().toString().trim(),bitmap);
                    String encodedImage = imageToString(bitmap);
                    SharedPrefarences.setPreference(getContext(),FirebaseAuth.getInstance().getCurrentUser().getUid(),encodedImage);
                }
 //               break;

       //     default:
     //           break;
   //     }


    // create user method for creating new user in the app and Firebase
    private void createUser(String n, String e, String id, String s, String g, String num){
        if(id==null){
            //
        }
        // initializing the data model class of the userDataModel
        userDataModelClass user = new userDataModelClass(n,e,id,s,g,num);
        firedbReference.child(id).setValue(user);

        addUserChangeLisener(id);
    }

    // user change listener for updating user information in the current user  (Without Login Out)
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

    //update user information by calling this method.

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


    // Reading user data using this method and storing the image of user profile in shared preference.

    private void readData(String userId) throws NullPointerException{


    // converting the iomage from string to image
        final Bitmap bitmapImage = stringToImage(SharedPrefarences.getPreference(getContext(),usrID));


        firedbReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userDataModelClass user   = dataSnapshot.getValue(userDataModelClass.class);
                // fetching all the data
                // from firebase servers and placing them in UI
                if (user != null){


                Log.d(TAG, "Social Media: " + user.social_media +
                        "\n email " + user.user_email);



                profile_name.setText(user.user_name);
                profile_email.setText(user.user_email);
                profile_group.setText(user.group);
                profile_social.setText(user.social_media);
                profile_number.setText(user.number);
                profile_image.setImageBitmap(bitmapImage);}

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }


    // custom method for converting the image to string
    private String imageToString(Bitmap bitmaps){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }

    // custom method for converting the string to image.
    private Bitmap stringToImage(String encodedImage){

        byte[] decodedByte = Base64.decode(encodedImage, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);

    }

   // server side code for image storing on the custom servers of routemasterbd
//    private void uploadImg(String imageName, Bitmap b){
//        String image_data = imageToString(b);
//        String image_name = imageName;
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        Log.e(TAG,"image receiving"+image_name);
//
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<ImageClass> call = apiInterface.uploadImage(uid,image_name,image_data);
//
//        call.enqueue(new Callback<ImageClass>() {
//            @Override
//            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
//
//                ImageClass imageClass = response.body();
//                Toast.makeText(ProfileActivity.this,"Server Response: "+imageClass.getReseponse(),Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<ImageClass> call, Throwable t) {
//
//                Toast.makeText(ProfileActivity.this,"Server Response: "+"Upload Faliure ",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

//    private class UploadImage extends AsyncTask<Void, Void, Void> {
//        Bitmap image;
//        String Uid;
//        String name;
//        String uriencodedImage;
//        String encodedName;
//        String data;
//
//        public UploadImage(Bitmap image, String name) {
//
//            this.image = image;
//            this.name = name;
//
//        }
//
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//
//            byte[] imgbyte = byteArrayOutputStream.toByteArray();
//            String encodedImage = Base64.encodeToString(imgbyte, Base64.DEFAULT);
//
//            try {
//                encodedName = URLEncoder.encode(name, "UTF-8");
//                uriencodedImage = URLEncoder.encode(encodedImage,"UTF-8");
//            }catch (Exception e){
//
//            }
//
//             data = encodedName+"."+uriencodedImage;
//            sendData(data);
//
//            return null;
//        }
//
//
//        private void sendData(String data){
//
//            try{
//                URL url  = new URL("www.routemasterbd.info/android/uploadImage.php");
//
//                URLConnection connection = url.openConnection();
//                connection.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
//                wr.write(data);
//                wr.flush();
//            }catch (Exception e){
//
//            }
//        }
    }













