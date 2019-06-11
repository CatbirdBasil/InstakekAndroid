package com.mobiledev.edu.instakek.ui.activity

import android.os.Bundle
import android.text.Html
import com.mobiledev.edu.instakek.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BottomNavigationActivity(4) {

    private val Tag = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        setButtonValue();
    }

    fun setUsername() {

    }

    //TODO delete this
    fun setButtonValue() {
        //get subscriber , group , subscribtion
        val subscriber: Int = 10
        val group: Int = 20
        val subscribtion: Int = 30
        //

        subscriptions_button.setText(Html.fromHtml("<font color=\"grey\"><big>subscribtion<big></font><br/><big>" + subscribtion + "<big>"))
        subscriber_button.setText(Html.fromHtml("<font color=\"grey\"><big>subscriber<big></font><br/><big>" + subscriber + "<big>"))
        group_button.setText(Html.fromHtml("<font color=\"grey\"><big>group<big></font><br/><big>" + group + "<big>"))
    }
}
