package com.mobiledev.edu.instakek.ui.activity

import android.os.Bundle
import com.mobiledev.edu.instakek.R


class ChatActivity : BottomNavigationActivity(1) {

    private val Tag = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()

    }
}
