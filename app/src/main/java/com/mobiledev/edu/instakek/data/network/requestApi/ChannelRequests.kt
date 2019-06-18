package com.mobiledev.edu.instakek.data.network.requestApi

import com.mobiledev.edu.instakek.data.database.entity.Channel
import com.mobiledev.edu.instakek.data.network.utils.NetworkConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChannelRequests {

    companion object {
        const val ENDPOINT_URL: String = NetworkConstants.CHANNEL_ENDPOINT_URL
    }

    @GET("$ENDPOINT_URL/{id}")
    fun getChannelById(@Path("id") id: Long): Call<Channel>

    @GET("$ENDPOINT_URL/base/{id}")
    fun getBaseChannelByUserId(@Path("id") id: Long): Call<Channel>
}