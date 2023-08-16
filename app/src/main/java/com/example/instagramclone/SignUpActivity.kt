package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.instagramclone.Data.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SignUpActivity : AppCompatActivity() {

    lateinit var database : DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        val signUpBtn = findViewById<Button>(R.id.signUpBtn);
        val etName = findViewById<TextInputEditText>(R.id.username)
        val etEmail = findViewById<TextInputEditText>(R.id.useremail)
        val etPassword = findViewById<TextInputEditText>(R.id.password)
        val tv = findViewById<TextView>(R.id.tv1)

        signUpBtn.setOnClickListener {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (password.isEmpty() || email.isEmpty() || name.isEmpty()) {

                Toast.makeText(this, "Enter FULL Details", Toast.LENGTH_SHORT).show()
            }

            else if(password.length <= 5){
                Toast.makeText(this, "Password must be aleast 6 characters", Toast.LENGTH_SHORT).show()
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Verify E-mail Address", Toast.LENGTH_SHORT).show()
            }



            else {
                    database = FirebaseDatabase.getInstance().getReference("Users")
                    auth = FirebaseAuth.getInstance()

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                // Sign in success, update UI with the signed-in user's information

                                val userid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
                                val myuser = User(name, email, password,"",userid)
                                database.child(userid).setValue(myuser).addOnSuccessListener {

                                    Toast.makeText(this, "User Registerd Succesfully", Toast.LENGTH_SHORT).show();
                                    val x = Intent(this, MainActivity::class.java);
                                    finish()
                                    startActivity(x);

                                    etName.text?.clear()
                                    etEmail.text?.clear()
                                    etPassword.text?.clear()

                                }.addOnFailureListener {
                                    Toast.makeText(this, "Sign-Up fail", Toast.LENGTH_SHORT,).show()
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Sign-Up failed.", Toast.LENGTH_SHORT,).show()
                            }
                        }
            }

        }

        tv.setOnClickListener {

            val x = Intent(this, LoginActivity::class.java)
            startActivity(x);
        }

    }
}