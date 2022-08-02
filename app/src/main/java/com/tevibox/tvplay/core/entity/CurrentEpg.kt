package com.tevibox.tvplay.core.entity

data class CurrentEpg(
    val currentEpg: Epg?,
    val nextEpg: Epg?,
    val progressEpg: Int
)
