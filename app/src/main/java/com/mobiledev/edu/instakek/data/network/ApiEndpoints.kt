package com.mobiledev.edu.instakek.data.network

import com.mobiledev.edu.instakek.data.network.requestApi.AuthRequests

object ApiEndpoints {

    private val retrofitClient by lazy {
        NetworkUtils.retrofitClient
    }

    val Auth by lazy {
        retrofitClient.create(AuthRequests::class.java)
    }

}