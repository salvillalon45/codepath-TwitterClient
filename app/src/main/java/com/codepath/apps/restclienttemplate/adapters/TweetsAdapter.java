package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.TweetModel;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<TweetModel> tweetModelList;

    // Pass in the context and the list of tweets
    public TweetsAdapter(Context newContext, List<TweetModel> newList) {
        this.context = newContext;
        this.tweetModelList = newList;
    }

    // For each row, inflate a layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        TweetModel tweetModel = tweetModelList.get(position);

        // Bind the tweet with the view holder
        holder.bind(tweetModel);
    }

    @Override
    public int getItemCount() {
        return tweetModelList.size();
    }

    // Clean all elements in the recycler view
    public void clear() {
        tweetModelList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<TweetModel> tweetsList) {
        tweetModelList.addAll(tweetsList);
    }


    // Define a viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;

        // itemView represents one row in the recycler view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
        }

        public void bind(TweetModel tweetModel) {
            tvBody.setText(tweetModel.body);
            tvScreenName.setText(tweetModel.userModel.screenName);
            Glide.with(context).load(tweetModel.userModel.profileImageUrl).into(ivProfileImage);
        }
    }
}
