package com.example.instagramclone.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Data.Posts
import com.example.instagramclone.HomeFragment
import com.example.instagramclone.R

class HomeAdapter(var postArray: ArrayList<Posts>, var context: HomeFragment):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.each_home_item, parent, false)
        return HomeViewHolder(itemview)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentitem = postArray[position]
        holder.username.text = currentitem.username
        holder.postText.text = currentitem.postText
        if(currentitem.profileimg != ""){
        Glide.with(context).load(currentitem.profileimg).into(holder.profileImg)}
        Glide.with(context).load(currentitem.postImg).into(holder.postImg)
    }


    override fun getItemCount(): Int {
        return postArray.size
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.profile_img)
        val username = itemView.findViewById<TextView>(R.id.textView2)
        val postImg = itemView.findViewById<ImageView>(R.id.imageView2)
        val postText = itemView.findViewById<TextView>(R.id.textView)
    }
}

