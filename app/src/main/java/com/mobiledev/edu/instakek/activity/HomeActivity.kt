package com.mobiledev.edu.instakek.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mobiledev.edu.instakek.R

class HomeActivity : BaseActivity(0) {

    private val Tag = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()

    }
}
