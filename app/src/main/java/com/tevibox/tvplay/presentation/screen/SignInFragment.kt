package com.tevibox.tvplay.presentation.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.exception.InvalidCredentialsException
import com.tevibox.tvplay.core.exception.InvalidEmailException
import com.tevibox.tvplay.core.exception.InvalidPasswordException
import com.tevibox.tvplay.databinding.FragmentSignInBinding
import com.tevibox.tvplay.presentation.viewmodel.SignInViewModel
import com.tevibox.tvplay.utils.extension.clearErrorsAfterTextChanged
import com.tevibox.tvplay.utils.extension.hideKeyboard
import com.tevibox.tvplay.utils.extension.showErrorIcon

class SignInFragment
    : BaseBindingFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    override fun getViewModelClass() = SignInViewModel::class

    @SuppressLint("SetTextI18n")
    override fun onViewBindingCreated(binding: FragmentSignInBinding) {
        binding.viewModel = viewModel
        binding.accountEditText.clearErrorsAfterTextChanged()
        binding.passwordEditText.clearErrorsAfterTextChanged()

        binding.passwordEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }

        binding.submitButton.setOnClickListener {
            val email = binding.accountEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.authUser(email, password)
        }
        binding.logoImageView.setOnClickListener {
            if (BuildConfig.DEBUG) {
                binding.accountEditText.setText("qwerty1234@gmail.com")
                binding.passwordEditText.setText("qwerty1234")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountEditText.requestFocus()
    }

    override fun showException(throwable: Throwable) {
        when (throwable) {
            is InvalidCredentialsException -> processValidationExceptions(throwable)
            else -> super.showException(throwable)
        }
    }

    private fun processValidationExceptions(throwable: InvalidCredentialsException) {
        throwable.exceptions.forEach {
            when (it) {
                is InvalidEmailException -> binding.accountEditText.showErrorIcon()
                is InvalidPasswordException -> binding.passwordEditText.showErrorIcon()
                else -> super.showException(throwable)
            }
        }
    }
}