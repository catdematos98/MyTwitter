package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String name;
    public long uid;
    public String screenName;
    public String profileImageURL;
    public boolean verified;

    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageURL = json.getString("profile_image_url");
        user.verified = json.getBoolean("verified");
        Log.i("Verified: ", user.name + user.verified);
        return user;
    }
}
