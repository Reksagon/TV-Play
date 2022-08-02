package com.tevibox.tvplay.utils.glide

import android.graphics.drawable.Drawable
import android.widget.EditText
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class RightCompoundDrawableGlideTarget(
    private val editText: EditText
) : CustomTarget<Drawable>() {

    override fun onResourceReady(
        resource: Drawable,
        transition: Transition<in Drawable>?
    ) {
        resource.setBounds(0, 0, resource.intrinsicWidth, resource.intrinsicHeight)
        editText.setCompoundDrawables(null, null, resource, null)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
    }
}