package com.tevibox.tvplay.core.entity

data class Streams(
    val error: Int,
    val streams: List<Stream>,
    val time: Long
)
