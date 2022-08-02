package com.tevibox.tvplay.core.entity

data class User(
    val email: String,
    val subscriptionStatus: String,
    val subscriptionExpire: Long,
    val card: String
)
