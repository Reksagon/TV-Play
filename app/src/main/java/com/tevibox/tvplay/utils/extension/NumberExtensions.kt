package com.tevibox.tvplay.utils.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(pattern: String = "dd-MM-yyyy"): String {
    val date = Date(this)
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(date)
}