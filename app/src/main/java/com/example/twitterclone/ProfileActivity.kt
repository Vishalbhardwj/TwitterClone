package com.example.twitterclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.net.URI
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    private lateinit var circleImage:CircleImageView
    private lateinit var btnUpload: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()

        btnUpload.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1001)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1001 && resultCode == RESULT_OK){
            circleImage.setImageURI(data?.data)
            uploadImage(data?.data)
        }
    }

    private fun uploadImage(uri :Uri?){
        val profileImage = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().getReference().child("ProfileImages/$profileImage")
        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
                    .child("profileImage").setValue(it.toString())

            }

        }

    }

    private fun init(){
        circleImage = findViewById(R.id.profile_image)
        btnUpload  = findViewById(R.id.btn_upload)

        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   val link = snapshot.child("profileImage").value.toString()
                    if(!link.isNullOrBlank()){
                        Glide.with(this@ProfileActivity)
                            .load(link)
                            .into(circleImage)
                    }else{
                        circleImage.setImageResource(R.drawable.ic_launcher_foreground)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ProfileActivity, "Unable to get Image", Toast.LENGTH_SHORT).show()
                }

            })
    }

}