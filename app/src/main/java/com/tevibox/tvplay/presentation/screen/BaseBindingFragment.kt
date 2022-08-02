package com.tevibox.tvplay.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel

abstract class BaseBindingFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes protected val contentLayoutId: Int
) : BaseFragment<VM>() {

    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        onViewBindingCreated(binding)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected open fun onViewBindingCreated(binding: VB) {
        // none
    }
}