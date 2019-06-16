package com.mobiledev.edu.instakek.data.network.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkUtils {

    //private const val API_BASE_URL = "http://10.0.2.2:8080/api/"
    private const val API_BASE_URL = "https://instakek.herokuapp.com/api/"

    var retrofitClient: Retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()
                    .newBuilder()
                    .authenticator(TokenAuthenticator())
                    .build())
            .build()

    object Constants {
        const val AUTH_ENDPOINT_URL: String = "auth"
    }

}