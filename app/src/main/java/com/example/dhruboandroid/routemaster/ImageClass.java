package com.example.dhruboandroid.routemaster;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dhrubo Android on 6/23/2018.
 */

public class ImageClass {

    @SerializedName("uid")
    private String uid;

    @SerializedName("image_name")
    private String image_name;

    @SerializedName("image_data")
    private String image_data;

    @SerializedName("response")
    private String reseponse;

    public String getReseponse() {
        return reseponse;
    }
}
