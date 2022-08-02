package com.tevibox.tvplay.presentation.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.tevibox.tvplay.R

class TransparentProgressDialog : DialogFragment(R.layout.dialog_transparent_progress) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    companion object {
        fun newInstance() = TransparentProgressDialog()
    }
}