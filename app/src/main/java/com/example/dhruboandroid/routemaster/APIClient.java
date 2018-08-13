package com.example.dhruboandroid.routemaster;

/**
 * Created by Dhrubo Android on 7/6/2018.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhrubo Android on 7/6/2018.
 * APIClient for calling the API
 */

class APIClient {

    public static final String BASE_URL = "http://routemasterbd.info/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}