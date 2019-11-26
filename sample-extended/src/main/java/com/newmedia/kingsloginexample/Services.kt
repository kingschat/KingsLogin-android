package com.newmedia.kingsloginexample

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface KingschatService {
    @POST("oauth2/token")
    fun createToken(@Body body: AccessTokenRequest): Call<AccessTokenResponse>
}

object Services {
    val kingschat: KingschatService = createKingschatService()

    private fun createKingschatService(): KingschatService {
        val gson = createGson()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://connect.kingsch.at/api/developer/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(KingschatService::class.java)
    }

    private fun createGson(): Gson = GsonBuilder()
            .setLenient()
            .create()
}