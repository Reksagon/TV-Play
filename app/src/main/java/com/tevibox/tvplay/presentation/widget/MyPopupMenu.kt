package com.tevibox.tvplay.presentation.widget

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu

class MyPopupMenu constructor(
    context: Context,
    anchor: View
) : PopupMenu(context, anchor) {

    var selectedItem: MenuItem? = null

    fun setOnCancelListener(listener: () -> Unit) {
        setOnDismissListener {
            if (selectedItem == null) {
                listener()
            }
            selectedItem = null
        }
    }

    override fun setOnMenuItemClickListener(listener: OnMenuItemClickListener?) {
        super.setOnMenuItemClickListener {
            selectedItem = it
            listener?.onMenuItemClick(it) ?: false
        }
    }
}