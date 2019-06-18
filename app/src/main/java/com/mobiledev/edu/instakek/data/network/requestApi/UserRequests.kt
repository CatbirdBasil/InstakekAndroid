package com.mobiledev.edu.instakek.data.network.requestApi

import com.mobiledev.edu.instakek.data.database.entity.User
import com.mobiledev.edu.instakek.data.network.utils.NetworkConstants
import retrofit2.Call
import retrofit2.http.*

interface UserRequests {

    companion object {
        const val ENDPOINT_URL: String = NetworkConstants.USER_ENDPOINT_URL
    }

    @GET(ENDPOINT_URL)
    fun getAll(): Call<List<User>>

    @GET("$ENDPOINT_URL/{id}")
    fun getById(@Path("id") id: Long): Call<User>

    @POST(ENDPOINT_URL)
    fun insert(@Body user: User): Call<User>

    @PUT(ENDPOINT_URL)
    fun update(@Body user: User): Call<Void>

    @DELETE("$ENDPOINT_URL/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}