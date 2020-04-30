package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class TweetDetailActivity extends AppCompatActivity {

    public static final String TAG = "TweetDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tweet_detail);
        Log.i(TAG, "Inside onCreate() for TweetDetailActivity");

    }
}
