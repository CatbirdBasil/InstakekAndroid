package com.mobiledev.edu.instakek.data.network.utils

import com.mobiledev.edu.instakek.data.network.requestApi.AuthRequests
import com.mobiledev.edu.instakek.data.network.requestApi.ChannelRequests
import com.mobiledev.edu.instakek.data.network.requestApi.PostRequests
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

    val Post by lazy {
        retrofitClient.create(PostRequests::class.java)
    }

    val Channel by lazy {
        retrofitClient.create(ChannelRequests::class.java)
    }

}