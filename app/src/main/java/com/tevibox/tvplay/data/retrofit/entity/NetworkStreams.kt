package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkStreams(
    val error: Int,
    @SerializedName("streams") val streams: Map<String, NetworkStream>,
    val time: Long
)
