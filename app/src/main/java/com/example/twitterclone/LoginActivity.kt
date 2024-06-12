package com.example.twitterclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.twitterclone.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogIn:Button
    private lateinit var btnSignUp:Button

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        if(auth.currentUser != null){
            val intent1 = Intent(this,HomeActivity::class.java)
            startActivity(intent1)
        }

        btnLogIn.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            loginUser(email,password)
        }

        btnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signupUser(email,password)
        }

        }




    private fun init(){

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogIn = findViewById(R.id.btn_login)
        btnSignUp= findViewById(R.id.btn_signUp)

        auth = Firebase.auth

    }

    private fun loginUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent1 = Intent(this,HomeActivity::class.java)
                    startActivity(intent1)
                    finish()


                } else {
                    Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signupUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val listoffollowing = mutableListOf<String>()
                    listoffollowing.add("")
                    val listoftweet = mutableListOf<String>()
                    listoftweet.add("")
                    val user = User(
                        userEmail = email,
                        userPassword = password,
                        listOfFollowing = listoffollowing,
                        listOfTweet = listoftweet,
                        profileImage = "",
                        uid = auth.uid.toString()
                    )

                    addUserToDB(user)

                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDB(user:User){
        Firebase.database.getReference("users").child(user.uid).setValue(user)
    }



}