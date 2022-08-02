package com.tevibox.tvplay.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.tevibox.tvplay.R

class MenuContentConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.layout_menu_content_constraint, this)
    }
}