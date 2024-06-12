package com.example.twitterclone.data

data class User(
    val userEmail:String = "",
    val userPassword:String = "",
    val listOfFollowing:List<String> = listOf(),
    val listOfTweet:List<String> = listOf(),
    val profileImage:String = "",
    val uid:String = ""

)
