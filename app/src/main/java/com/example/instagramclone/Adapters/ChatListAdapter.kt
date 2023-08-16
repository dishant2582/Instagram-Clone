package com.example.instagramclone.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.ChatActivity
import com.example.instagramclone.Data.User
import com.example.instagramclone.R


class ChatListAdapter(var usersArray: ArrayList<User>, var context: Context):
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.each_chat_list_item, parent, false)
        return ChatListViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return usersArray.size
    }


    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentitem = usersArray[position]
        holder.username.text = currentitem.name
        if(currentitem.profileimg != ""){
            Glide.with(context).load(currentitem.profileimg).into(holder.profileImg)
        }

        holder.itemView.setOnClickListener{

            val x = Intent(context, ChatActivity::class.java)

            x.putExtra("name", currentitem.name)
            x.putExtra("uid", currentitem.id)

            context.startActivity(x)
        }
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.profile_img)
        val username = itemView.findViewById<TextView>(R.id.textView5)

    }

}