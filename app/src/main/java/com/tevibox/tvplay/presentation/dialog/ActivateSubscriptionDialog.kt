package com.tevibox.tvplay.presentation.dialog

import android.content.DialogInterface
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogActivateSubscriptionBinding
import com.tevibox.tvplay.utils.extension.clearErrorsAfterTextChanged
import com.tevibox.tvplay.utils.extension.showErrorIcon

class ActivateSubscriptionDialog(
    private val title: String,
    private val text: String,
    private val onSubmitClick: (String) -> Unit
) : BaseDialog<DialogActivateSubscriptionBinding>(R.layout.dialog_activate_subscription) {

    private var onLogoutClick: (() -> Unit)? = null

    override fun onViewBindingCreated(binding: DialogActivateSubscriptionBinding) {
        binding.titleTextView.text = title
        binding.messageTextView.text = text
        binding.activationCodeEditText.clearErrorsAfterTextChanged()
        binding.submitButton.setOnClickListener {
            val code = binding.activationCodeEditText.text.toString()
            if (code.isEmpty()) {
                binding.activationCodeEditText.showErrorIcon()
                return@setOnClickListener
            }
            onSubmitClick(code)
        }

        binding.logoutButton.setOnClickListener { onLogoutClick?.invoke() }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        requireActivity().finish()
    }

    fun showErrorMessage(text: String) {
        binding.errorTextView.isVisible = true
        binding.errorTextView.text = text
    }

    fun hideErrorMessage() {
        binding.errorTextView.isVisible = false
    }

    fun showProgress() {
        binding.submitButton.isInvisible = true
        binding.submitButtonProgress.isVisible = true
    }

    fun hideProgress() {
        binding.submitButton.isInvisible = false
        binding.submitButtonProgress.isVisible = false
    }

    fun setOnLogoutClickListener(onLogoutClick: (() -> Unit)?): ActivateSubscriptionDialog {
        this.onLogoutClick = onLogoutClick
        return this
    }
}