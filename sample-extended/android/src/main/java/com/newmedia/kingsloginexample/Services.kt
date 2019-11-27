package com.newmedia.kingsloginexample

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newmedia.kingslogin.model.CreateFolderRequest
import com.newmedia.kingslogin.model.CreateFolderResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KingschatService {
    @POST("oauth2/token")
    fun createToken(@Body body: AccessTokenRequest): Call<AccessTokenResponse>
}

interface KingscloudService {
    @Headers("Accept: application/x-protobuf", "Content-Type: application/x-protobuf")
    @POST("folders")
    fun createDirectory(@Header("Authorization") authToken: String, @Body request: CreateFolderRequest): Call<CreateFolderResponse>
}

object Services {
    val kingschat: KingschatService = createKingschatService()
    val kingscloud: KingscloudService = createKingscloudService()

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

    private fun createKingscloudService(): KingscloudService {
        val client = createClient()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://kingscloud.co/api/")
                .client(client)
                .addConverterFactory(ProtoConverterFactory.create())
                .build()

        return retrofit.create(KingscloudService::class.java)
    }

    private fun createClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private fun createGson(): Gson = GsonBuilder()
            .setLenient()
            .create()
}