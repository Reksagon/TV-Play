package com.tevibox.tvplay.utils

import android.util.Patterns

object StringUtils {

    fun isValidEmail(email: String) =
        email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String) = password.isNotEmpty() && password.length > 5
}