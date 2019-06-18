package com.mobiledev.edu.instakek.utils

import android.content.Context
import android.widget.Toast
import com.mobiledev.edu.instakek.R


object ActivityUtils {

    fun showNoInternetToast(applicationContext: Context) {
        Toast.makeText(applicationContext,
                applicationContext.getString(R.string.no_internet_error), Toast.LENGTH_LONG).show()
    }
}