package com.newmedia.kingsloginexample

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val client = createClient()

        val gson = createGson()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://connect.kingsch.at/developer/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(KingschatService::class.java)
    }

    private fun createGson(): Gson = GsonBuilder()
            .setLenient()
            .create()

    private fun createClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}