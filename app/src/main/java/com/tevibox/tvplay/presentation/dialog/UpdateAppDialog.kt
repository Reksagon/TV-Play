package com.tevibox.tvplay.presentation.dialog

import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogUpdateAppBinding

class UpdateAppDialog(
    private val oldVersion: String,
    private val newVersion: String,
    private val onSubmitClick: () -> Unit
) : BaseDialog<DialogUpdateAppBinding>(R.layout.dialog_update_app) {

    override fun onViewBindingCreated(binding: DialogUpdateAppBinding) {
        binding.updateButton.setOnClickListener {
            onSubmitClick()
            dismiss()
        }
        binding.oldVersionTextView.text = oldVersion
        binding.newVersionTextView.text = newVersion
    }
}