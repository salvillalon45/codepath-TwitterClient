package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.adapters.TweetsAdapter;
import com.codepath.apps.restclienttemplate.models.TweetModel;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";

    TwitterClient client;
    RecyclerView rvTweets;
    List<TweetModel> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Log.i("TimelineActivity", "Inside onCreate()");

        client = TwitterApplication.getRestClient(this);

        swipeContainer = findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Inside onRefresh(), fetching new data");
                populateHomeTimeline();
            }
        });

        // Find the recycler view
        rvTweets = findViewById(R.id.rvTweets);

        // Initialize the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);

        // Recycler view setup: layout manager and the adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "Inside onLoadMore " + page);
                loadMoreData();
            }
        };
        // Adds the scroll listener to the recycler view
        rvTweets.addOnScrollListener(scrollListener);
        populateHomeTimeline();
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTimeline);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_launcher_twitter);
    }

//    @Override
//    public void onItemClick(int position) {
//        Toast.makeText(
//                TimelineActivity.this,
//                "An item at position " + position + " clicked!",
//                Toast.LENGTH_SHORT).show();
//
//        // Handle item click here:
////                Log.d("BookListActivity", String.valueOf(abooks.get(position)));
////                Log.d("BookListActivity", String.valueOf(abooks.get(position).getAuthor()));
////                Log.d("BookListActivity", String.valueOf(abooks.get(position).getTitle()));
//
//        // Get Book at the given position
//        TweetModel tweetModel = tweets.get(position);
//
//        // Pass the book into details activity using extras and Create Intent to start BookDetailActivity
//        Intent intent = new Intent(TimelineActivity.this, TweetDetailActivity.class);
//
//        // Put information we want to send to the activity
//        intent.putExtra("tweet", Parcels.wrap(tweetModel));
//        startActivity(intent);
//    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void loadMoreData() {
        // Send an API request to retrieve appropriate paginated data
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Inside onSuccess of loadMoreData(): " + json.toString());

                //  --> Deserialize and construct new model objects from the API response
                JSONArray jsonArray = json.jsonArray;

                try {
                    List<TweetModel> tweets = TweetModel.fromJsonArray(jsonArray);
                    //  --> Append the new data objects to the existing set of items inside the array of items
                    //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
                    // addAll also notifies adapter of changes
                    adapter.addAll(tweets);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "Inside onFailure of loadMoreData()", throwable);
            }
        }, tweets.get(tweets.size() - 1).id);
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.

    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Inside onSuccess() of populateHomeTimeline " + json.toString());

                JSONArray jsonArray = json.jsonArray;

                try {
                    adapter.clear();
                    adapter.addAll(TweetModel.fromJsonArray(jsonArray));
//                    adapter.notifyDataSetChanged();
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);
                }
                catch (JSONException e) {
                    Log.e(TAG, "There is a json exception");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Inside onFailure() of populateHomeTimeline" + response, throwable);
            }
        });
    }

}
