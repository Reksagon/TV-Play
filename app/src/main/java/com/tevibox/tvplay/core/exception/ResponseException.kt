package com.tevibox.tvplay.core.exception

import com.javavirys.core.exception.BaseException

class ResponseException(
    val code: Int,
    private val msg: String
) : BaseException() {

    override fun toString() = msg
}