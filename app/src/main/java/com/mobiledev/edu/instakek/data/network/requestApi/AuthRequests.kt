package com.mobiledev.edu.instakek.data.network.requestApi

import com.mobiledev.edu.instakek.data.network.request.LoginRequest
import com.mobiledev.edu.instakek.data.network.response.LoginResponse
import com.mobiledev.edu.instakek.data.network.utils.NetworkConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRequests {

    companion object {
        const val ENDPOINT_URL: String = NetworkConstants.AUTH_ENDPOINT_URL
    }

    @POST("$ENDPOINT_URL/signin")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}