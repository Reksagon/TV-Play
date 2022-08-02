package com.tevibox.tvplay.presentation.dialog

import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogRenewSubscriptionBinding
import com.tevibox.tvplay.utils.extension.clearErrorsAfterTextChanged
import com.tevibox.tvplay.utils.extension.showErrorIcon

class RenewSubscriptionDialog(
    private val onSubmitClick: (String) -> Unit
) : BaseDialog<DialogRenewSubscriptionBinding>(R.layout.dialog_renew_subscription) {

    override fun onViewBindingCreated(binding: DialogRenewSubscriptionBinding) {
        binding.activationCodeEditText.clearErrorsAfterTextChanged()
        binding.submitButton.setOnClickListener {
            val code = binding.activationCodeEditText.text.toString()
            if (code.isEmpty()) {
                binding.activationCodeEditText.showErrorIcon()
                return@setOnClickListener
            }
            onSubmitClick(code)
            dismiss()
        }
    }

    companion object {
        fun newInstance(onSubmitClick: (String) -> Unit) = RenewSubscriptionDialog(onSubmitClick)
    }
}