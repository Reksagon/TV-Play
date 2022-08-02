package com.tevibox.tvplay.core.entity

data class Category(
    override val id: Int,
    val name: String,
    override var selected: Boolean = false,
    var requestFocus: Boolean = false
) : SelectableEntityInterface