package com.tevibox.tvplay.presentation.dialog

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogToastBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ToastDialog : BaseDialog<DialogToastBinding>(R.layout.dialog_toast), CoroutineScope {

    private var message: String? = null

    @StringRes
    private var messageId: Int = 0

    private var job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onViewBindingCreated(binding: DialogToastBinding) {
        binding.messageTextView.text = if (messageId == 0) {
            message
        } else {
            getString(messageId)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun setMessage(message: String): ToastDialog {
        this.message = message
        return this
    }

    fun setMessage(@StringRes messageId: Int): ToastDialog {
        this.messageId = messageId
        return this
    }

    fun show(
        manager: FragmentManager,
        durationInSec: Int
    ) {
        launch(Dispatchers.IO) {
            for (i in 1..durationInSec) {
                delay(1000)
            }
            withContext(Dispatchers.Main) { dismiss() }
        }
        show(manager)
    }
}