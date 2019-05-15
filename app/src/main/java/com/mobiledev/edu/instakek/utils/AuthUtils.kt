package com.mobiledev.edu.instakek.utils

import android.content.Context
import android.preference.PreferenceManager

object AuthUtils {

    //    private val LOGIN_KEY = "pref_login_key"
//    private val PASSWORD_KEY = "pref_password_key"
    private const val JWT_TOKEN_KEY = "pref_jwt_token_key"
    //
//    val DEFAULT_LOGIN = "unknown"
//    val DEFAULT_PASSWORD = "unknown"
    const val DEFAULT_JWT_TOKEN = "unknown"

    private const val TOKEN_TYPE = "Bearer "
    var TOKEN: String = DEFAULT_JWT_TOKEN
        private set

    private fun getPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
//
//    fun saveLogin(context: Context, login: String) =
//        getPreferences(context).edit().putString(LOGIN_KEY, login).apply()
//
//    fun getLogin(context: Context): String =
//        getPreferences(context).getString(LOGIN_KEY, DEFAULT_LOGIN)
//
//    fun savePassword(context: Context, password: String) =
//        getPreferences(context).edit().putString(PASSWORD_KEY, password).apply()
//
//
//    fun getPassword(context: Context): String =
//            getPreferences(context).getString(PASSWORD_KEY, DEFAULT_PASSWORD)
//

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