package com.example.instagramclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.instagramclone.Adapters.HomeAdapter
import com.example.instagramclone.Adapters.SearchAdapter
import com.example.instagramclone.Data.Posts
import com.example.instagramclone.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SearchFragment : Fragment() {

    private lateinit var searchView: View
    lateinit var searchRecycle: RecyclerView
    lateinit var usersArray: ArrayList<User>
    lateinit var db : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchView = inflater.inflate(R.layout.fragment_search, container, false)

        searchRecycle = searchView.findViewById<RecyclerView>(R.id.search_recycle)
        searchRecycle.layoutManager = LinearLayoutManager(activity)

        usersArray = arrayListOf<User>()

        getuserData()

        return searchView
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

                    val searchAdapter = SearchAdapter(usersArray, this@SearchFragment);
                    searchRecycle.adapter = searchAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}