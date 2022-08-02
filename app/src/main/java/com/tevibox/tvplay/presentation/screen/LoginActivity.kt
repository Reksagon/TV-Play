package com.tevibox.tvplay.presentation.screen

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.ActivityLoginBinding
import com.tevibox.tvplay.presentation.viewmodel.LoginViewModel

class LoginActivity
    : BaseBindingActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun getViewModelClass() = LoginViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val set = AnimatorInflater.loadAnimator(this, R.animator.logo_animator) as AnimatorSet
        set.setTarget(binding.logo)
        set.start()
        viewModel.navigateToNextScreen()
    }

    override fun onBackPressed() = finish()
}