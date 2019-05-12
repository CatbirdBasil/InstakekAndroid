package com.mobiledev.edu.instakek.data.network

import com.mobiledev.edu.instakek.data.network.requestApi.AuthRequests
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object NetworkUtils {

    private const val API_BASE_URL = "http://10.0.2.2:8080/api/"
    //private const val API_BASE_URL = "https://instakek.herokuapp.com/api"

    var retrofitClient : Retrofit = Retrofit.Builder()
            .baseUrl(NetworkUtils.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()
                    .newBuilder()
                    .authenticator(TokenAuthenticator())
                    .build())
            .build()

    object Constants {
        const val AUTH_ENDPOINT_URL : String = "auth"
    }

    object Api {

        val Auth by lazy {
            retrofitClient.create(AuthRequests::class.java)
        }

    }


}