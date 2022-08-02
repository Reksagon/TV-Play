package com.tevibox.tvplay.core.entity

import androidx.annotation.Keep

@Keep
data class DeviceInformation(
    @Transient val deviceId: String,
    val androidVersion: Int,
    val appVersion: Int,
    val brand: String,
    val model: String,
    val firmwareVersion: String
)
