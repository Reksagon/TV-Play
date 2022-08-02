package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkStream(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("descr") val description: String,
    @SerializedName("cats") val categories: List<String>?,
    @SerializedName("epg") val epgList: List<NetworkEpg>?,
    @SerializedName("url") val url: String
)
