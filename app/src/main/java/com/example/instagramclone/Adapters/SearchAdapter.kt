package com.example.instagramclone.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Data.Posts
import com.example.instagramclone.Data.User
import com.example.instagramclone.HomeFragment
import com.example.instagramclone.R
import com.example.instagramclone.SearchFragment

class SearchAdapter(var postArray: ArrayList<User>, var context: SearchFragment):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.each_search_item, parent, false)
        return SearchViewHolder(itemview)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentitem = postArray[position]
        holder.username.text = currentitem.name
        if(currentitem.profileimg != ""){
            Glide.with(context).load(currentitem.profileimg).into(holder.profileImg)
        }

    }

    override fun getItemCount(): Int {
        return postArray.size
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.profile_img)
        val username = itemView.findViewById<TextView>(R.id.textView5)

    }
}