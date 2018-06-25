package com.example.dhruboandroid.routemaster;

/**
 * Created by Dhrubo Android on 6/22/2018.
 * This class is used for storing the user data to firebase real time database
 * Data is stored using JSON
 * nested JSON is used for storing multiple data entry using the firebase auth key of the current logged in user
 */

public class userDataModelClass{

    public String user_email;
    public String user_name;
    public String user_id;
    public String social_media;
    public String group;
    public String number;


    public userDataModelClass(){

    }

    public userDataModelClass(String email, String name, String id, String social, String grp, String num){
        user_email = email;
        user_name = name;
        user_id = id;
        social_media = social;
        group = grp;
        number = num;
    }

}
