package com.example.dhruboandroid.routemaster;

/**
 * Created by Dhrubo Android on 7/6/2018.
 */
/**
 * Created by Dhrubo Android on 7/6/2018.
 *STOP data class for stoppage data
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("to_place")
    @Expose
    private String toPlace;

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

}
