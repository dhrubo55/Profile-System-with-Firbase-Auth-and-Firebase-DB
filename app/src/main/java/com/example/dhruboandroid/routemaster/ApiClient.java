package com.example.dhruboandroid.routemaster;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhrubo Android on 6/23/2018.
 */

public class ApiClient {

    private static final String BaseUrl = "http://routemasterbd.info/android/UploadedImages/";


    private static Retrofit retrofit;



    public static Retrofit getApiClient(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
