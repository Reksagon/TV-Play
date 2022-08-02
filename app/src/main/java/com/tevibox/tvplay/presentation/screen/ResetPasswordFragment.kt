package com.tevibox.tvplay.presentation.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.exception.InvalidEmailException
import com.tevibox.tvplay.databinding.FragmentForgotPasswordBinding
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.viewmodel.ResetPasswordViewModel
import com.tevibox.tvplay.utils.extension.hideErrorIcon
import com.tevibox.tvplay.utils.extension.hideKeyboard
import com.tevibox.tvplay.utils.extension.showErrorIcon
import timber.log.Timber

class ResetPasswordFragment :
    BaseBindingFragment<FragmentForgotPasswordBinding, ResetPasswordViewModel>(R.layout.fragment_forgot_password) {

    override fun getViewModelClass() = ResetPasswordViewModel::class

    @SuppressLint("SetTextI18n")
    override fun onViewBindingCreated(binding: FragmentForgotPasswordBinding) {
        clearErrorsForEditText(binding.accountEditText)

        binding.accountEditText.setOnKeyListener { _, keyCode, event ->
            Timber.d("keyCode: $keyCode")
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }

        binding.submitButton.setOnClickListener {
            val email = binding.accountEditText.text.toString()
            viewModel.resetPassword(email)
        }

        binding.logoImageView.setOnClickListener {
            if (BuildConfig.DEBUG) {
                binding.accountEditText.setText("qwerty123@gmail.com")
            }
        }
    }

    private fun clearErrorsForEditText(editText: EditText) {
        editText.doAfterTextChanged {
            editText.hideErrorIcon()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountEditText.requestFocus()
    }

    override fun initializeLiveData() {
        viewModel.resetPasswordFlagLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    ToastDialog().setMessage(getString(R.string.forgot_password_reset_instructions))
                        .show(childFragmentManager, 2)
                }
                else -> {}
            }
        }
    }

    override fun showException(throwable: Throwable) {
        when (throwable) {
            is InvalidEmailException -> binding.accountEditText.showErrorIcon()
            else -> ToastDialog().setMessage(throwable.toString())
                .show(childFragmentManager, 2)
        }
    }
}