package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapters.ChatListAdapter
import com.example.instagramclone.Adapters.SearchAdapter
import com.example.instagramclone.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatListActivity : AppCompatActivity() {

    lateinit var ChatListRecycle: RecyclerView
    lateinit var usersArray: ArrayList<User>
    lateinit var db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        supportActionBar?.hide()

        ChatListRecycle = findViewById(R.id.chatlistrecycleview)
        ChatListRecycle.layoutManager = LinearLayoutManager(this)

        usersArray = arrayListOf<User>()

        getuserData()

    }

    private fun getuserData() {

        db = FirebaseDatabase.getInstance().getReference("Users")

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                usersArray.clear()

                if(snapshot.exists()){

                    for(x in snapshot.children){

                        val userdata = x.getValue(User::class.java)

                        if(FirebaseAuth.getInstance().currentUser?.uid.toString() != userdata?.id) {
                            usersArray.add(userdata!!)
                        }
                    }

                    val chatlistAdapter = ChatListAdapter(usersArray, this@ChatListActivity);
                    ChatListRecycle.adapter = chatlistAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}