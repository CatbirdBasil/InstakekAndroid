package com.mobiledev.edu.instakek.activity

import android.os.Bundle
import com.mobiledev.edu.instakek.R


class GroupActivity : BaseActivity(3) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
    }


}
