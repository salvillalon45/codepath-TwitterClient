package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {

    public String name;
    public String screenName;
    public String profileImageUrl;

    public static UserModel fromJson(JSONObject jsonObject) throws JSONException {
        UserModel userModel = new UserModel();
        userModel.name = jsonObject.getString("name");
        userModel.screenName = jsonObject.getString("screen_name");
        userModel.profileImageUrl = jsonObject.getString("profile_image_url_https");

        return userModel;
    }
}
