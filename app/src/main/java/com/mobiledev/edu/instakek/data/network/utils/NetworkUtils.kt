package com.mobiledev.edu.instakek.data.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkUtils {

    //private const val API_BASE_URL = "http://10.0.2.2:8080/api/"
    private const val API_BASE_URL = "https://instakek.herokuapp.com/api/"

    var retrofitClient: Retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory
                    .create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZZ").create()))
            .client(OkHttpClient()
                    .newBuilder()
                    .authenticator(TokenAuthenticator())
                    .build())
            .build()

    fun isOnline(applicationContext: Context): Boolean {

        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo ?: return false

        when (activeNetwork.type) {
            ConnectivityManager.TYPE_WIFI ->
                if ((activeNetwork.state == NetworkInfo.State.CONNECTED
                                || activeNetwork.state == NetworkInfo.State.CONNECTING))
                    return true
            ConnectivityManager.TYPE_MOBILE ->
                if ((activeNetwork.state == NetworkInfo.State.CONNECTED
                                || activeNetwork.state == NetworkInfo.State.CONNECTING))
                    return true
            else -> return false
        }
        return false
    }

}