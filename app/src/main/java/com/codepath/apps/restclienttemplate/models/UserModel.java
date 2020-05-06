package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class UserModel {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screenName;

    @ColumnInfo
    public String profileImageUrl;

    // Empty constructor for Parceler library
    public UserModel() {}

    public static UserModel fromJson(JSONObject jsonObject) throws JSONException {
        UserModel userModel = new UserModel();
        userModel.id = jsonObject.getLong("id");
        userModel.name = jsonObject.getString("name");
        userModel.screenName = jsonObject.getString("screen_name");
        userModel.profileImageUrl = jsonObject.getString("profile_image_url_https");

        return userModel;
    }

    public static List<UserModel> fromJsonTweetArray(List<TweetModel> tweetsFromNetwork) {
        List<UserModel> users = new ArrayList<>();

        for (int i = 0; i < tweetsFromNetwork.size(); i++) {
            users.add(tweetsFromNetwork.get(i).userModel);
        }

        return users;
    }
}
