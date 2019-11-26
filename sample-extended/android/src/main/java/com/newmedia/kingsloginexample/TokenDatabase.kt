package com.newmedia.kingsloginexample

import android.content.Context

class TokenDatabase(context: Context) {

    companion object {
        const val PREFERENCES_KEY = "authorization"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, 0)

    fun getAccessToken(): String = sharedPreferences.getString(ACCESS_TOKEN, "").orEmpty()

    fun getRefreshToken(): String = sharedPreferences.getString(REFRESH_TOKEN, "").orEmpty()

    fun setAccessToken(token: String) = sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()

    fun setRefreshToken(token: String) = sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()

    fun clear() = sharedPreferences.edit().clear().apply()
}