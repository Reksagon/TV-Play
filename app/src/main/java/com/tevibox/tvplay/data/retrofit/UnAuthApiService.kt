package com.tevibox.tvplay.data.retrofit

import com.tevibox.tvplay.data.retrofit.entity.NetworkBaseResponse
import com.tevibox.tvplay.data.retrofit.entity.NetworkUserToken
import okhttp3.ResponseBody
import retrofit2.http.*

interface UnAuthApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerSubscriber(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device") deviceId: String,
        @Field("info") deviceInfo: String
    ): NetworkUserToken

    @FormUrlEncoded
    @POST("auth")
    suspend fun authSubscriber(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device") deviceId: String,
        @Field("info") deviceInfo: String
    ): NetworkUserToken

    @FormUrlEncoded
    @POST("auth")
    suspend fun authSubscriber(
        @Field("device") deviceId: String,
        @Field("info") deviceInfo: String
    ): NetworkUserToken

    @FormUrlEncoded
    @POST("renew")
    suspend fun updateToken(
        @Header("X-LU-AUTH") authHash: String,
        @Field("device") deviceId: String,
        @Field("info") deviceInfo: String
    ): NetworkUserToken

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPassword(@Field("email") email: String): NetworkBaseResponse

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody
}