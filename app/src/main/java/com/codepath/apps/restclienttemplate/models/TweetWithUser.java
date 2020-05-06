package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

public class TweetWithUser {

    // Embedded notation flattens the properties of the User object into the object, preserving encapsulation
    @Embedded
    UserModel userModel;

    @Embedded(prefix = "tweet_")
    TweetModel tweetModel;

    public static List<TweetModel> getTweetList(List<TweetWithUser> tweetWithUsers) {
        List<TweetModel> tweetModels = new ArrayList<>();

        for (int i = 0; i < tweetWithUsers.size(); i++) {
            TweetModel tweetModel = tweetWithUsers.get(i).tweetModel;
            tweetModel.userModel = tweetWithUsers.get(i).userModel;
            tweetModels.add(tweetModel);
        }

        return tweetModels;
    }
}
