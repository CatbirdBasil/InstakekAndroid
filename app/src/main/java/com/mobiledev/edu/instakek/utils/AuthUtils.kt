package com.mobiledev.edu.instakek.utils

import android.content.Context
import android.preference.PreferenceManager

object AuthUtils {

    private const val JWT_TOKEN_KEY = "pref_jwt_token_key"
    private const val CURRENT_USER_ID_KEY = "pref_current_user_id_key"
    private const val TOKEN_TYPE = "Bearer "

    const val DEFAULT_JWT_TOKEN = "unknown"
    const val DEFAULT_USER_ID: Long = 0
    var TOKEN: String = DEFAULT_JWT_TOKEN
        private set

    var CURRENT_USER_ID: Long = DEFAULT_USER_ID
        private set

    private fun getPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveJwtToken(context: Context, token: String) {
        getPreferences(context).edit().putString(JWT_TOKEN_KEY, token).apply()
        TOKEN = token
    }

    private fun getJwtToken(context: Context): String =
            getPreferences(context).getString(JWT_TOKEN_KEY, DEFAULT_JWT_TOKEN)

    fun saveCurrentUserId(context: Context, id: Long) {
        getPreferences(context).edit().putLong(CURRENT_USER_ID_KEY, id).apply()
        CURRENT_USER_ID = id;
    }

    private fun getCurrentUserId(context: Context): Long =
            getPreferences(context).getLong(CURRENT_USER_ID_KEY, DEFAULT_USER_ID)

    fun initUserData(context: Context) {
        TOKEN = getJwtToken(context)
        CURRENT_USER_ID = getCurrentUserId(context)
    }

    fun clearUserData(context: Context) {
        TOKEN = DEFAULT_JWT_TOKEN
        CURRENT_USER_ID = DEFAULT_USER_ID
        saveCurrentUserId(context, DEFAULT_USER_ID)
        saveJwtToken(context, DEFAULT_JWT_TOKEN)
    }


    fun getBearerToken(): String = TOKEN_TYPE + TOKEN


}