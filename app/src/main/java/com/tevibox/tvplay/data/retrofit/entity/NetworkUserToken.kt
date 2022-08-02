package com.tevibox.tvplay.data.retrofit.entity

data class NetworkUserToken(
    val error: Int,
    val hash: String,
    val expire: Long,
    val time: Long,
)
