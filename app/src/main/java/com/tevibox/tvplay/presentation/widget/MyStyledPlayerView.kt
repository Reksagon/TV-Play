package com.tevibox.tvplay.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.google.android.exoplayer2.ui.StyledPlayerView

class MyStyledPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : StyledPlayerView(context, attrs, defStyleAttr) {

    override fun dispatchKeyEvent(event: KeyEvent) = when (event.keyCode) {
        KeyEvent.KEYCODE_ENTER,
        KeyEvent.KEYCODE_DPAD_CENTER,
        KeyEvent.KEYCODE_DPAD_LEFT,
        KeyEvent.KEYCODE_DPAD_RIGHT -> false
        else -> super.dispatchKeyEvent(event)
    }
}