package com.tevibox.tvplay.core.entity

data class UserCredentials(
    val email: String = "",
    val password: String = "",
    val device: String,
    val info: String,
    val confirmPassword: String = ""
)
