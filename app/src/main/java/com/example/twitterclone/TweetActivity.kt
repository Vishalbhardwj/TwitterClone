package com.example.twitterclone

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {
    private lateinit var edtTweet:EditText
    private lateinit var btnUploadTweet:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        init()

        btnUploadTweet.setOnClickListener {
            val tweet = edtTweet.text.toString()
            uploadTweetDb(tweet)
        }

    }

    private fun uploadTweetDb(tweet:String){
        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoftweet = snapshot.child("listOfTweet").value as MutableList<String>
                    listoftweet.add(tweet)
                    uploadListOfTweet(listoftweet)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun uploadListOfTweet(listoftweet:List<String>){
        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .child("listOfTweet").setValue(listoftweet)
        Toast.makeText(this, "Uploaded Tweet successfully", Toast.LENGTH_SHORT).show()

    }
    private fun init(){
        edtTweet = findViewById(R.id.edt_enter_tweet)
        btnUploadTweet = findViewById(R.id.btn_upload_tweet)
    }
}