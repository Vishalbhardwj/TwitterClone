package com.example.twitterclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.twitterclone.adapters.VPadapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var fab:FloatingActionButton
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager:ViewPager2
    private lateinit var vPAdapter:VPadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        TabLayoutMediator(tabLayout,viewPager){ tab: TabLayout.Tab, position: Int ->
            when(position){
                0 -> tab.text = "Accounts"
                else -> tab.text = "Tweets"
            }

        }.attach()

        fab.setOnClickListener {
            startActivity(Intent(this,TweetActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       
        menuInflater.inflate(R.menu.menutop, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){

            R.id.menu_logOut ->{

                auth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))

            }else ->{
            startActivity(Intent(this,ProfileActivity::class.java))
            }
        }
        return true

    }

    private fun init(){
        auth = Firebase.auth
        fab = findViewById(R.id.fab)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        vPAdapter = VPadapter(this)
        viewPager.adapter = vPAdapter

    }


}