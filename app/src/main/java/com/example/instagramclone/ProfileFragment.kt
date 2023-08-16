package com.example.instagramclone

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.instagramclone.Data.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.values
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {

    private lateinit var profileView: View
    lateinit var db: DatabaseReference
    lateinit var db2: DatabaseReference
    private lateinit var imgstorage: FirebaseStorage
    private lateinit var imguri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        profileView =  inflater.inflate(R.layout.fragment_profile, container, false)

        val pickimg = profileView.findViewById<Button>(R.id.button3)
        val uploadImg = profileView.findViewById<Button>(R.id.button4)
        val imgview = profileView.findViewById<ImageView>(R.id.imageView4)
        val username = profileView.findViewById<TextView>(R.id.username)
        var imgurl = "";
        var flag = false
        imgstorage = FirebaseStorage.getInstance()

        db = FirebaseDatabase.getInstance().getReference("Users");
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        db.child(userid).get().addOnSuccessListener {

            if(it.child("profileimg").value != ""){
            imgurl = it.child("profileimg").value.toString()
            Glide.with(this).load(imgurl).into(imgview);
            }

            username.text = it.child("name").value.toString()
        }


        val galleryimg = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imgview.setImageURI(it)
                if (it != null) {
                    imguri = it
                }
            }
        )

        pickimg.setOnClickListener {
            galleryimg.launch("image/*")
            flag = true
        }

        uploadImg.setOnClickListener {

            if (flag == true) {

                imgstorage.getReference("Images").child(System.currentTimeMillis().toString())
                    .putFile(imguri)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { uri ->

                                val userid = FirebaseAuth.getInstance().currentUser!!.uid

                                db2 = FirebaseDatabase.getInstance().getReference("Users")

                                db2.child(userid).child("profileimg").setValue(uri.toString())
                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            activity,
                                            "Successfully Post!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }.addOnFailureListener {
                                    Toast.makeText(activity, "Failed Post!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }.addOnFailureListener {
                        Toast.makeText(activity, "Failed Post!", Toast.LENGTH_SHORT).show()
                    }

                flag = false
            }

            else{
                Toast.makeText(activity, "Pick Image to upadte Profile", Toast.LENGTH_SHORT).show()
            }
        }

        return profileView
    }
}