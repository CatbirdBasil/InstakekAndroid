package com.mobiledev.edu.instakek.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import com.mobiledev.edu.instakek.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BottomNavigationActivity(4) {

    private val Tag = "ProfileActivity"

    private lateinit var cratePost : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        setButtonValue();
        cratePost = findViewById(R.id.create_post)

        cratePost!!.setOnClickListener{getToCreatePost()}

    }

    private fun getToCreatePost() {
        var intent: Intent = Intent(this, CreatePostActivity::class.java)

        startActivity(intent)
        finish()
    }

    fun setUsername() {

    }

    //TODO delete this
    fun setButtonValue() {



    }
}
