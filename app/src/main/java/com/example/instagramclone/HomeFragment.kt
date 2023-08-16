package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapters.HomeAdapter
import com.example.instagramclone.Data.Posts
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    lateinit var homeview : View
    lateinit var homerecycle: RecyclerView
    lateinit var postarray: ArrayList<Posts>
    lateinit var db : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeview = inflater.inflate(R.layout.fragment_home, container, false)

        val msg = homeview.findViewById<ImageView>(R.id.imageView)
        homerecycle = homeview.findViewById<RecyclerView>(R.id.homeRecycle)

        homerecycle.layoutManager = LinearLayoutManager(activity)

        postarray = arrayListOf<Posts>()

        getuserData()

        msg.setOnClickListener {

            val x = Intent(activity, ChatListActivity::class.java)
            startActivity(x);
        }

        return homeview
    }

    private fun getuserData() {

        db = FirebaseDatabase.getInstance().getReference("Posts")

        db.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(x in snapshot.children){

                        val userpost = x.getValue(Posts::class.java)
                        postarray.add(userpost!!)
                    }

                    val homeAdapter = HomeAdapter(postarray, this@HomeFragment);
                    homerecycle.adapter = homeAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}