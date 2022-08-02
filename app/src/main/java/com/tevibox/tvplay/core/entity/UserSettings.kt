package com.tevibox.tvplay.core.entity

data class UserSettings(
    val categoryName: String,
    val channelName: String,
    val channelUrl: String,
    val tokenHash: String,
    val tokenExpire: Long,
    val tokenTime: Long
)
