package com.example.dhruboandroid.routemaster;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dhrubo Android on 6/23/2018.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST("upload_image_to_server.php")
    retrofit2.Call<ImageClass> uploadImage(@Field("uid")String uid, @Field("image_name") String image_name, @Field("image_data") String image_data);
}
