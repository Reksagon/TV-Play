package com.tevibox.tvplay.presentation.dialog

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import timber.log.Timber

class ProgressDialogInterface(
    private val activity: Activity
) : ProgressInterface {

    private var progressDialog: DialogFragment? = null

    override fun show() {
        val progressDialog: DialogFragment = getProgressDialog()
        if (progressDialog.isAdded) {
            Timber.d("progress dialog already shown")
            return
        }

        val fragmentTransaction = (activity as AppCompatActivity)
            .supportFragmentManager
            .beginTransaction()

        fragmentTransaction.remove(progressDialog)
        progressDialog.show(fragmentTransaction, ProgressDialogInterface::class.java.name)
    }

    override fun hide() {
        progressDialog?.let {
            it.dismissAllowingStateLoss()
            progressDialog = null
        }
    }

    private fun getProgressDialog(): DialogFragment {
        if (progressDialog == null) {
            progressDialog = TransparentProgressDialog.newInstance()
        }
        return progressDialog!!
    }
}