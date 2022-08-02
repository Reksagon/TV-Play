package com.tevibox.tvplay.utils.extension

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

fun DrawerLayout.doOnDrawerClosed(block: (drawerView: View) -> Unit) = addDrawerListener(
    object : DrawerLayout.SimpleDrawerListener() {
        override fun onDrawerClosed(drawerView: View) {
            block(drawerView)
        }
    }
)