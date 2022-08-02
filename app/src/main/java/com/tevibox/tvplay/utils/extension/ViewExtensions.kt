package com.tevibox.tvplay.utils.extension

import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.tevibox.tvplay.R
import com.tevibox.tvplay.utils.glide.RightCompoundDrawableGlideTarget

fun TextView.setTextStyle(style: Int) = setTypeface(typeface, style)

fun EditText.showErrorIcon(@DrawableRes iconId: Int = R.drawable.ic_baseline_error_outline_24) {
    Glide.with(context)
        .load(iconId)
        .into(RightCompoundDrawableGlideTarget(this))
}

fun EditText.hideErrorIcon() {
    setCompoundDrawables(null, null, null, null)
}

fun EditText.clearErrorsAfterTextChanged() = doAfterTextChanged {
    hideErrorIcon()
}