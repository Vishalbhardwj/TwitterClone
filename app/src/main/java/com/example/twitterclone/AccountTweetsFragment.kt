package com.example.twitterclone

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.TweetAdapter
import com.example.twitterclone.data.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccountTweetsFragment:Fragment() {
    private lateinit var rv:RecyclerView
    private lateinit var tweetadapter:TweetAdapter
    private var listOfTweet= mutableListOf<Tweet>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_tweets,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv  = view.findViewById(R.id.tweet_rv)

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowingForTweet = snapshot.child("listOfFollowing").value as MutableList<String>
                    listOfFollowingForTweet.add(FirebaseAuth.getInstance().uid.toString())
                    listOfFollowingForTweet.forEach{
                        getTweetFromUid(it)
                    }


                }

                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })


    }

    private fun getTweetFromUid(uid:String){
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listOfTweetUID = mutableListOf<String>()
                     snapshot.child("listOfTweet").value?.let {
                         listOfTweetUID = it as MutableList<String>
                     }

                    listOfTweetUID.forEach {
                        if(!it.isNullOrBlank()){
                            listOfTweet.add(Tweet(it))
                        }

                    }
                    tweetadapter = TweetAdapter(listOfTweet)
                    rv.layoutManager = LinearLayoutManager(requireContext())
                    rv.adapter = tweetadapter

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}