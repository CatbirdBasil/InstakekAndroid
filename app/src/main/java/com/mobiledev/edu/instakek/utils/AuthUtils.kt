package com.mobiledev.edu.instakek.utils

import android.content.Context
import android.preference.PreferenceManager

object AuthUtils {

    private const val JWT_TOKEN_KEY = "pref_jwt_token_key"
    private const val TOKEN_TYPE = "Bearer "

    const val DEFAULT_JWT_TOKEN = "unknown"
    var TOKEN: String = DEFAULT_JWT_TOKEN
        private set

    private fun getPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveJwtToken(context: Context, token: String) {
        getPreferences(context).edit().putString(JWT_TOKEN_KEY, token).apply()
        TOKEN = token
    }

    private fun getJwtToken(context: Context): String =
            getPreferences(context).getString(JWT_TOKEN_KEY, DEFAULT_JWT_TOKEN)

    fun initJwtToken(context: Context) {
        TOKEN = getJwtToken(context)
    }

    fun getBearerToken(): String = TOKEN_TYPE + TOKEN


}