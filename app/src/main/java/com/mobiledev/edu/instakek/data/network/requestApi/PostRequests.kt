package com.mobiledev.edu.instakek.data.network.requestApi

import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.network.utils.NetworkConstants
import retrofit2.Call
import retrofit2.http.*

interface PostRequests {

    companion object {
        const val ENDPOINT_URL: String = NetworkConstants.POST_ENDPOINT_URL
    }

    @GET(ENDPOINT_URL)
    fun getAll(): Call<List<Post>>

    @GET("$ENDPOINT_URL/subscribed")
    fun getPostsFromSubscribedChannels(): Call<List<Post>>

    @GET("$ENDPOINT_URL/comments/{id}")
    fun getPostWithComments(@Path("id") id: Long): Call<Post>

    @POST(ENDPOINT_URL)
    fun insert(@Body post: Post): Call<Post>

    @PUT(ENDPOINT_URL)
    fun update(@Body post: Post): Call<Unit>

    @DELETE("$ENDPOINT_URL/{id}")
    fun delete(@Path("id") id: Long): Call<Unit>

    @POST("$ENDPOINT_URL/like/{id}")
    fun likePost(@Path("id") id: Long): Call<Unit>

    @DELETE("$ENDPOINT_URL/like/{id}")
    fun unlikePost(@Path("id") id: Long): Call<Unit>

//    fun getPostsFromSubscribedChannels(@Path("id") id: Int): Call<List<Post>>




}