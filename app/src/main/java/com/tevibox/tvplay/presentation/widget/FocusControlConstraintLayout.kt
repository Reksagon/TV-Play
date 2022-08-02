package com.tevibox.tvplay.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import timber.log.Timber

open class FocusControlConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    var onFocusSearchListener: ((View?, View?, Int) -> View?)? = null

    override fun focusSearch(focused: View?, direction: Int): View? {
        Timber.d("focusSearch: focused = $focused && direction = $direction")
        val newViewFocused = super.focusSearch(focused, direction)
        Timber.d("focusSearch: newViewFocused = $newViewFocused")

        return onFocusSearchListener?.invoke(focused, newViewFocused, direction)
    }
}