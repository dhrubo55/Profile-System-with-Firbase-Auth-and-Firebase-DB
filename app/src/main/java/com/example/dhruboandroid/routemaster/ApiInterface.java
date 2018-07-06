package com.example.dhruboandroid.routemaster;

/**
 * Created by Dhrubo Android on 7/6/2018.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Dhrubo Android on 7/6/2018.
 *API interface for retrofit use
 */

public interface ApiInterface {

    @Headers({
            "X-API-KEY:rm635109"
    })
    @FormUrlEncoded
    @POST("index.php/vehicles/getVehicleByRoute")
    Call<List<Businfo>> getVehicleByRoute(@Field("from") String from, @Field("to") String to);
}
