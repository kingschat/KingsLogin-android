package com.newmedia.kingsloginexample

import android.content.Context

class TokenDatabase(context: Context) {

    companion object {
        const val PREFERENCES_KEY = "authorization"
        const val ACCESS_TOKEN = "access_token"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, 0)

    fun getAccessToken(): String = sharedPreferences.getString(ACCESS_TOKEN, "").orEmpty()

    fun getAuthorizationToken(): String = "Bearer ${getAccessToken()}"

    fun putAccessToken(token: String) = sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
}