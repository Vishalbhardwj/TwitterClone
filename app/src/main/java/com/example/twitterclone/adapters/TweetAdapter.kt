package com.example.twitterclone.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.data.Tweet


class TweetAdapter (
    private val tweetlist: List<Tweet>,
    ):RecyclerView.Adapter<TweetAdapter.viewHolder>() {

        class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
           val tweet:TextView = itemView.findViewById(R.id.account_tweet)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.account_tweets,parent,false)
            return viewHolder(view)
        }

        override fun getItemCount(): Int {
            return tweetlist.size
        }

        override fun onBindViewHolder(holder: viewHolder, position: Int) {
            val currentUser = tweetlist[position]
           holder.tweet.text = currentUser.contentTweet
        }


}