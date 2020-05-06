package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {

    @Query("SELECT TweetModel.body AS tweet_body, TweetModel.createdAt AS tweet_createdAt, TweetModel.id AS tweet_id, UserModel.* FROM TweetModel INNER JOIN UserModel ON TweetModel.userId = UserModel.id ORDER BY createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // Any number of tweets in array
    void insertModel(TweetModel... tweetModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(UserModel... userModels);
}
