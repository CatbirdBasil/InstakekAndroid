package com.mobiledev.edu.instakek.activity

import android.os.Bundle
import android.util.Log
import com.mobiledev.edu.instakek.R


class ChatActivity : BaseActivity(1) {

    private val Tag = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()

    }
}
