package com.mobiledev.edu.instakek.data.network

import com.mobiledev.edu.instakek.utils.AuthUtils
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }

    override fun authenticate(route: Route?, response: Response?): Request {

        val accessToken = AuthUtils.getBearerToken()

        return response!!.request().newBuilder()
                .header(AUTHORIZATION, accessToken)
                .build()
    }
}