package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkEpg(
    @SerializedName("start") val start: Long,
    @SerializedName("stop") val stop: Long,
    @SerializedName("title") val title: String,
    @SerializedName("descr") val description: String,
)
