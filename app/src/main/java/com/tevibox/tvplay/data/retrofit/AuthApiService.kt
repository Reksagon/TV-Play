package com.tevibox.tvplay.data.retrofit

import com.tevibox.tvplay.data.retrofit.entity.NetworkActivationCode
import com.tevibox.tvplay.data.retrofit.entity.NetworkSettingsAnswer
import com.tevibox.tvplay.data.retrofit.entity.NetworkStreams
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @POST("info")
    suspend fun getCurrentUserInfo(): NetworkUser

    @POST("streams")
    suspend fun getStreams(): NetworkStreams

    @FormUrlEncoded
    @POST("streams")
    suspend fun getStreams(@Field("category") category: String): NetworkStreams

    @POST("settings")
    suspend fun getSettings(): NetworkSettingsAnswer

    @FormUrlEncoded
    @POST("activate")
    suspend fun activate(@Field("card") card: String): NetworkActivationCode

    @FormUrlEncoded
    @POST("view")
    suspend fun view(@Field("stream_id") streamId: Int)

    @POST("keep-alive")
    suspend fun keepAlive()
}