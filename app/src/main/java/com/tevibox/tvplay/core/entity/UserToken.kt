package com.tevibox.tvplay.core.entity

data class UserToken(
    val hash: String,
    val expire: Long,
    val time: Long
) {

    companion object {
        fun buildEmptyToken() = UserToken("", 0L, 0L)
    }
}
