package com.tevibox.tvplay.presentation.dialog

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.DialogLinearProgressBinding

class LinearProgressDialog :
    BaseDialog<DialogLinearProgressBinding>(R.layout.dialog_linear_progress) {

    private val progressField = ObservableInt()

    private val titleField = ObservableField<String>()

    override fun onViewBindingCreated(binding: DialogLinearProgressBinding) {
        binding.title = titleField
        binding.progress = progressField
        binding.cancelButton.setOnClickListener {
            cancelListener?.invoke()
            dismiss()
        }
    }

    fun setTitle(title: String) {
        titleField.set(title)
    }

    fun setProgress(value: Int) {
        progressField.set(value)
    }
}