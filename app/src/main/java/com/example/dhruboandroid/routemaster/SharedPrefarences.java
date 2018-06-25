package com.example.dhruboandroid.routemaster;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Atiar on 5/23/18.
 */

public class SharedPrefarences {

    // Assigning the image of this camera as the default output of the sharedpreference if the firebase auth key dont have an entry in the shared preference
    Drawable drawable =ProfileActivity.getContext().getResources().getDrawable(R.drawable.ic_cameraa);
    private static Bitmap bitmap = BitmapFactory.decodeResource(ProfileActivity.getContext().getResources(),R.drawable.ic_cameraa);

    // custom method for encoding the image to base64 string
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private static final String PREFS_NAME = "preferenceName";
    private static final String DefImg = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100);


/*****************************//* Strat shared preferences *//******************************/


    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "None");
    }
    public static String getPreferenceProfile(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, DefImg);
    }

    public static boolean setPreferenceInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getPreferenceInt(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }
    public static int getPreferenceIntDrawable(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key,0);
    }

/*****************************//* End shared preferences *//******************************/


}