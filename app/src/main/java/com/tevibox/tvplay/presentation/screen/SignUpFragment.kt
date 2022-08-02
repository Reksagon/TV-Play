package com.tevibox.tvplay.presentation.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.exception.InvalidConfirmPasswordException
import com.tevibox.tvplay.core.exception.InvalidCredentialsException
import com.tevibox.tvplay.core.exception.InvalidEmailException
import com.tevibox.tvplay.core.exception.InvalidPasswordException
import com.tevibox.tvplay.databinding.FragmentSignUpBinding
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.viewmodel.SignUpViewModel
import com.tevibox.tvplay.utils.extension.hideErrorIcon
import com.tevibox.tvplay.utils.extension.hideKeyboard
import com.tevibox.tvplay.utils.extension.showErrorIcon

class SignUpFragment
    : BaseBindingFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {

    override fun getViewModelClass() = SignUpViewModel::class

    @SuppressLint("SetTextI18n")
    override fun onViewBindingCreated(binding: FragmentSignUpBinding) {
        binding.viewModel = viewModel

        clearErrorsForEditText(binding.accountEditText)
        clearErrorsForEditText(binding.passwordEditText)
        clearErrorsForEditText(binding.confirmPasswordEditText)

        binding.confirmPasswordEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }

        binding.submitButton.setOnClickListener {
            val email = binding.accountEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()
            viewModel.registerUser(email, password, confirmPassword)
        }

        binding.logoImageView.setOnClickListener {
            if (BuildConfig.DEBUG) {
                binding.accountEditText.setText("qwerty1234@gmail.com")
                binding.passwordEditText.setText("qwerty1234")
                binding.confirmPasswordEditText.text = binding.passwordEditText.text
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

    override fun showException(throwable: Throwable) {
        when (throwable) {
            is InvalidCredentialsException -> processValidationExceptions(throwable)
            else -> ToastDialog().setMessage(throwable.toString())
                .show(childFragmentManager, 2)
        }
    }

    private fun processValidationExceptions(throwable: InvalidCredentialsException) {
        throwable.exceptions.forEach {
            when (it) {
                is InvalidEmailException -> binding.accountEditText.showErrorIcon()
                is InvalidPasswordException -> binding.passwordEditText.showErrorIcon()
                is InvalidConfirmPasswordException -> binding.confirmPasswordEditText.showErrorIcon()
            }
        }
    }
}