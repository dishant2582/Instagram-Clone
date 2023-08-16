package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        val signInBtn = findViewById<Button>(R.id.loginBtn)
        val uniqueId = findViewById<TextInputEditText>(R.id.username)
        val password = findViewById<TextInputEditText>(R.id.password)
        val tv = findViewById<TextView>(R.id.tv2)

        signInBtn.setOnClickListener {

            val msg1 = uniqueId.text.toString()
            val msg2 = password.text.toString()

            if(msg1.isNotEmpty() && msg2.isNotEmpty())
            {
                readData(msg1, msg2)
            }
            else if(msg1.isEmpty() && msg2.isEmpty())
            {
                Toast.makeText(this,"Please enter your Email and Password", Toast.LENGTH_SHORT).show()
            }

            else if(msg2.isEmpty())
            {
                Toast.makeText(this,"Please enter your Password", Toast.LENGTH_SHORT).show()
            }

            else
            {
                Toast.makeText(this,"Please enter your Email", Toast.LENGTH_SHORT).show()
            }
        }

        tv.setOnClickListener {

            val x = Intent(this, SignUpActivity::class.java)
            startActivity(x)
        }

    }

    private fun readData(email: String, Password: String) {

            auth.signInWithEmailAndPassword(email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        val user = auth.currentUser
//                        updateUI(user)
                        Toast.makeText(this,"Login Successfull", Toast.LENGTH_SHORT).show()
                        val intentHomePage = Intent(this, MainActivity::class.java)
                        finish()
                        startActivity(intentHomePage)

                    } else {
                            Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT,).show()
                        }
                }
    }
}