package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkSettingsAnswer(
    @SerializedName("error") val error: Int,
    @SerializedName("settings") val settings: NetworkSettings,
    @SerializedName("time") val time: Long
)
