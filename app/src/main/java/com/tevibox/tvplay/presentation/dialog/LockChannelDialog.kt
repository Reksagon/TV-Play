package com.tevibox.tvplay.presentation.dialog

import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogLockChannelBinding
import com.tevibox.tvplay.utils.extension.clearErrorsAfterTextChanged
import com.tevibox.tvplay.utils.extension.showErrorIcon

class LockChannelDialog(
    private val onSubmitClick: (String) -> Unit
) : BaseDialog<DialogLockChannelBinding>(R.layout.dialog_lock_channel) {

    override fun onViewBindingCreated(binding: DialogLockChannelBinding) {
        binding.passwordEditText.clearErrorsAfterTextChanged()
        binding.submitButton.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            if (password.isEmpty() || password.length < 4) {
                binding.passwordEditText.showErrorIcon()
                return@setOnClickListener
            }
            onSubmitClick(password)
            dismiss()
        }
    }
}