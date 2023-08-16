package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapters.ChatAdapter
import com.example.instagramclone.Data.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var msgrecycle: RecyclerView
    private lateinit var messagelist: ArrayList<Message>
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name");
        val recieveruid = intent.getStringExtra("uid");

        var recieverRoom : String? = null
        var senderRoom : String? = null

        val etmsg = findViewById<EditText>(R.id.etmsg)
        val send = findViewById<ImageView>(R.id.sendImg)
        val title = findViewById<TextView>(R.id.textView9)

        title.text = name
        msgrecycle = findViewById(R.id.msgRecycle)

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        senderRoom = senderuid + recieveruid;
        recieverRoom = recieveruid + senderuid;

        msgrecycle.layoutManager = LinearLayoutManager(this)
        messagelist = arrayListOf<Message>()
        db = FirebaseDatabase.getInstance().getReference()

        db.child("Chats").child(senderRoom!!).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                messagelist.clear()
                if (snapshot.exists()) {

                    for (x in snapshot.children) {

                        val usermsg = x.getValue(Message::class.java)
                        messagelist.add(usermsg!!)
                    }
                }

                val chatadapeter = ChatAdapter(this@ChatActivity, messagelist)
                msgrecycle.adapter = chatadapeter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        send.setOnClickListener {

            val msg = etmsg.text.toString()

            val msgobj = Message(msg,senderuid)

            db.child("Chats").child(senderRoom!!).push().setValue(msgobj).addOnSuccessListener {
                db.child("Chats").child(recieverRoom!!).push().setValue(msgobj).addOnSuccessListener {
                    Toast.makeText(this, "Message Sent Successfully", Toast.LENGTH_SHORT,).show()
                }
            }
        }

    }
}