package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TweetModel {

    public String body;
    public String createdAt;
    public long id;
    public UserModel userModel;

    public static TweetModel fromJson(JSONObject jsonObject) throws JSONException {
        TweetModel tweetModel = new TweetModel();
        tweetModel.body = jsonObject.getString("text");
        tweetModel.createdAt = jsonObject.getString("created_at");
        tweetModel.id = jsonObject.getLong("id");
        tweetModel.userModel = UserModel.fromJson(jsonObject.getJSONObject("user"));

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
