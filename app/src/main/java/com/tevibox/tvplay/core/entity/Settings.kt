package com.tevibox.tvplay.core.entity

data class Settings(
    val keepAlive: Int,
    val streamsRefresh: Int,
    val settingsRefresh: Int,
    val categories: List<Category>,
    val appLatestVersion: Int,
    val appLatestVersionUrl: String
)
