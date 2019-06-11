package com.mobiledev.edu.instakek.data.network.utils

import com.mobiledev.edu.instakek.data.network.requestApi.AuthRequests
import com.mobiledev.edu.instakek.data.network.requestApi.UserRequests

object ApiEndpoints {

    private val retrofitClient by lazy {
        NetworkUtils.retrofitClient
    }

    val Auth by lazy {
        retrofitClient.create(AuthRequests::class.java)
    }

    val User by lazy {
        retrofitClient.create(UserRequests::class.java)
    }

}