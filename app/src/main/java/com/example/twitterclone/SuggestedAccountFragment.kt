package com.example.twitterclone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.SuggestedAccountAdapter
import com.example.twitterclone.data.SuggestedAccount
import com.example.twitterclone.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class SuggestedAccountFragment:Fragment() ,SuggestedAccountAdapter.ClickListener{
    private lateinit var suggestedAccountAdapter: SuggestedAccountAdapter
    private lateinit var accounts_recyleView:RecyclerView
    private  val listOfSuggestedAccount = mutableListOf<SuggestedAccount>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_suggested_accounts,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accounts_recyleView = view.findViewById(R.id.accounts_rv)

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listofFollowing = snapshot.child("listOfFollowing").value as MutableList<String>


                    FirebaseDatabase.getInstance().getReference().child("users")
                        .addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {

                                listOfSuggestedAccount.clear()

                                for(dataSnapshot in snapshot.children){
                                    val user = dataSnapshot.getValue(User::class.java)

                                    if(user?.uid.toString()!=FirebaseAuth.getInstance().uid.toString() && !listofFollowing.contains(user?.uid.toString())){
                                        val suggestedAccount = SuggestedAccount(user?.profileImage.toString(),user?.userEmail.toString(),user?.uid.toString())
                                        listOfSuggestedAccount.add(suggestedAccount)
                                        suggestedAccountAdapter = SuggestedAccountAdapter(listOfSuggestedAccount,requireContext(),this@SuggestedAccountFragment)
                                        accounts_recyleView.adapter = suggestedAccountAdapter
                                        accounts_recyleView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
                                    }
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
//
                            }

                        })

                }
                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })


    }

    private fun followUser(uid: String){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowing = snapshot.child("listOfFollowing").value as MutableList<String>
                    listOfFollowing.add(uid)

                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
                        .child("listOfFollowing").setValue(listOfFollowing)

                    Toast.makeText(requireContext(), " Successfully Followed ", Toast.LENGTH_SHORT).show()


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



    }

    override fun onClickedFollow(uid: String) {
            followUser(uid)
    }
}