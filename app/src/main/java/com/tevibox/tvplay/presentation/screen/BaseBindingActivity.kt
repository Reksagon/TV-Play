package com.tevibox.tvplay.presentation.screen

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel

abstract class BaseBindingActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes protected val contentLayoutId: Int
) : BaseActivity<VM>() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        onViewBindingCreated(binding)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    protected open fun onViewBindingCreated(binding: VB) {
        // none
    }
}