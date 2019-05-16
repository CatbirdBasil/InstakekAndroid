package com.mobiledev.edu.instakek.ui.activity
import android.os.Bundle
import com.mobiledev.edu.instakek.R


class GroupActivity : BottomNavigationActivity(3) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
    }


}
