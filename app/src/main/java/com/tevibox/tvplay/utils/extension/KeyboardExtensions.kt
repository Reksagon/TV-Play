package com.tevibox.tvplay.utils.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.fragment.app.Fragment

fun View.showKeyboard() {
    post {
        requestFocus()
        context.inputMethodManager?.showSoftInput(this@showKeyboard, SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    post {
        context.inputMethodManager?.hideSoftInputFromWindow(windowToken, HIDE_NOT_ALWAYS)
    }
}

fun Activity.showKeyboard() = currentFocus?.showKeyboard()

fun Activity.hideKeyboard() = currentFocus?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.showKeyboard()

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
