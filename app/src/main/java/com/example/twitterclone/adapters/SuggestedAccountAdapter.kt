package com.example.twitterclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitterclone.R
import com.example.twitterclone.data.SuggestedAccount
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class SuggestedAccountAdapter(
    private val listOfSuggestedAccount : List<SuggestedAccount>,
    private val context:Context,
    private var clickListener: ClickListener
):RecyclerView.Adapter<SuggestedAccountAdapter.viewHolder>() {

    class viewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image:CircleImageView = itemView.findViewById(R.id.image_account_profile)
        val email:TextView = itemView.findViewById(R.id.text_account_email)
        val btnfollow:Button = itemView.findViewById(R.id.btn_follow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.suggested_account,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfSuggestedAccount.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentUser = listOfSuggestedAccount[position]
        holder.email.text = currentUser.accountEmail
        Glide.with(context)
            .load(currentUser.profileImage)
            .into(holder.image)
        holder.btnfollow.setOnClickListener {
            clickListener.onClickedFollow(currentUser.uid.toString())
        }
    }

    interface ClickListener{
       fun onClickedFollow(uid:String)
    }
}