package com.tevibox.tvplay.core.entity

data class Epg(
    val start: Long,
    val stop: Long,
    val title: String,
    val description: String,
    var selected: Boolean = false
)
