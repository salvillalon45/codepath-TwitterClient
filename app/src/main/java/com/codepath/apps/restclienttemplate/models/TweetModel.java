package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = UserModel.class, parentColumns = "id", childColumns = "userId"))
public class TweetModel {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @Ignore
    public UserModel userModel;

    // Empty constructor for Parceler library
    public TweetModel() {}

    public static TweetModel fromJson(JSONObject jsonObject) throws JSONException {
        TweetModel tweetModel = new TweetModel();
        tweetModel.body = jsonObject.getString("text");
        tweetModel.createdAt = jsonObject.getString("created_at");
        tweetModel.id = jsonObject.getLong("id");
        UserModel userModel = UserModel.fromJson(jsonObject.getJSONObject("user"));
        tweetModel.userModel = userModel;
        tweetModel.userId = userModel.id;

        return tweetModel;
    }

    public static List<TweetModel> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<TweetModel> tweetModels = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            tweetModels.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweetModels;
    }

}
