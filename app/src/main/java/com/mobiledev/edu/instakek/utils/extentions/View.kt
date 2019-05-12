package com.mobiledev.edu.instakek.utils.extentions

import android.view.View

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.makeVisible() {
    visibility = View.VISIBLE;
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE;
}