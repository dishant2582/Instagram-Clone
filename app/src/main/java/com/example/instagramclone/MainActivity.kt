package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val bottomView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        //by default home page should be visible
        replaceWithFragment(HomeFragment())

        bottomView.setOnItemReselectedListener{

            when(it.itemId){

                R.id.home -> replaceWithFragment(HomeFragment())
                R.id.search_bar -> replaceWithFragment(SearchFragment())
                R.id.add_post -> replaceWithFragment(AddPostFragment())
                R.id.notification -> replaceWithFragment(NotificationFragment())
                R.id.profile -> replaceWithFragment(ProfileFragment())

                else ->{
                        Toast.makeText(this,"Sorry For ERRROR !",Toast.LENGTH_SHORT).show()
                }

            }
            true
        }
    }

    private fun replaceWithFragment(fragment: Fragment) {

        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}