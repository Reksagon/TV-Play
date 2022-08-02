package com.tevibox.tvplay.core.entity

data class Stream(
    override val id: Int,
    val name: String,
    val logo: String,
    val description: String,
    val categories: List<String>,
    val epgList: List<Epg>,
    val url: String,
    val number: Int = 0,
    override var selected: Boolean = false,
    var favorite: Boolean = false,
    var lock: Boolean = false,
    var password: String = "",
    var requestFocus: Boolean = false
) : SelectableEntityInterface