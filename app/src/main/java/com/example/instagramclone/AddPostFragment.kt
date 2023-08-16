package com.example.instagramclone

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Data.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.FirebaseStorageKtxRegistrar
import java.time.LocalDateTime


class AddPostFragment : Fragment() {

    lateinit var AddpostView: View
    lateinit var db: DatabaseReference
    lateinit var db2: DatabaseReference
    private lateinit var imgstorage: FirebaseStorage
    private lateinit var imguri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        AddpostView = inflater.inflate(R.layout.fragment_add_post, container, false)

        val postText = AddpostView.findViewById<EditText>(R.id.editText1)
        val pickImg = AddpostView.findViewById<Button>(R.id.button2)
        val post = AddpostView.findViewById<Button>(R.id.button)
        val imgview = AddpostView.findViewById<ImageView>(R.id.imageView3)
        var flag = false

        imgstorage = FirebaseStorage.getInstance()
        
        val galleryimg = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imgview.setImageURI(it)
                if (it != null) {
                    imguri = it
                }
            }
        )
        
        pickImg.setOnClickListener { 
            galleryimg.launch("image/*")
            flag = true
        }

        post.setOnClickListener {

            if(flag == true) {

                val postTitle = postText.text.toString()

                imgstorage.getReference("Images").child(System.currentTimeMillis().toString())
                    .putFile(imguri)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { uri ->

                                val userid = FirebaseAuth.getInstance().currentUser!!.uid

                                db = FirebaseDatabase.getInstance().getReference("Posts")

                                db2 = FirebaseDatabase.getInstance().getReference("Users")

                                db2.child(userid).get().addOnSuccessListener {

                                    val username = it.child("name").value.toString()
                                    val profileimg = it.child("profileimg").value.toString()

                                    val POST =
                                        Posts(username, postTitle, uri.toString(), profileimg)
                                    db.child(System.currentTimeMillis().toString()).setValue(POST)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                activity,
                                                "Successfully Post!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                activity,
                                                "Failed Post!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }.addOnFailureListener {
                                Toast.makeText(activity, "Failed Post!", Toast.LENGTH_SHORT).show()
                            }
                    }
                flag = false
            }
            else{
                Toast.makeText(activity, "Pick a Image To Upload!", Toast.LENGTH_SHORT).show()
            }
        }

        return AddpostView;
    }
}