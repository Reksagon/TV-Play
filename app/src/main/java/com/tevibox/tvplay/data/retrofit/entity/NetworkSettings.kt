package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkSettings(
    @SerializedName("keep_alive") val keepAlive: Int,
    @SerializedName("streams_refresh") val streamsRefresh: Int,
    @SerializedName("settings_refresh") val settingsRefresh: Int,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("app_latest_version") val appLatestVersion: Int,
    @SerializedName("app_latest_url") val appUrl: String
)
