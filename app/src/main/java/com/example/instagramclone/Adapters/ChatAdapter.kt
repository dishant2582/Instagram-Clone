package com.example.instagramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Data.Message
import com.example.instagramclone.R
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(var context: Context, val msgarray: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Item_recieve = 1;
    val Item_send = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){
            val itemview = LayoutInflater.from(parent.context).inflate(R.layout.recieve_msg, parent, false)
            return RecieveViewHolder(itemview)

        }else{
            val itemview = LayoutInflater.from(parent.context).inflate(R.layout.send_msg, parent, false)
            return SentViewHolder(itemview)
        }
    }

    override fun getItemCount(): Int {
        return msgarray.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentitem = msgarray[position]

        if(holder.javaClass == SentViewHolder::class.java){

            val viewHolder = holder as SentViewHolder
            holder.sentmsg.text = currentitem.msg

        } else{

            val viewHolder = holder as RecieveViewHolder
            holder.recievemsg.text = currentitem.msg
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentmsg = msgarray[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmsg.senderId)){
            return Item_send
        }else{
            return Item_recieve
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentmsg = itemView.findViewById<TextView>(R.id.senttext)
    }

    class RecieveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val recievemsg = itemView.findViewById<TextView>(R.id.recievetext)
    }

}