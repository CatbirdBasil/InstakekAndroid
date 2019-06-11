package com.mobiledev.edu.instakek.data.network.requestApi

import com.mobiledev.edu.instakek.data.network.request.LoginRequest
import com.mobiledev.edu.instakek.data.network.response.LoginResponse
import com.mobiledev.edu.instakek.data.network.utils.NetworkConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostRequests {

    companion object {
        const val ENDPOINT_URL: String = NetworkConstants.POST_ENDPOINT_URL
    }

    //@GET("$ENDPOINT_URL/")
    //fun getAll(): Call<List<Post>>


}