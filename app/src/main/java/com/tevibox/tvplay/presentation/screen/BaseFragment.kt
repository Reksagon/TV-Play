package com.tevibox.tvplay.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

@Suppress("LeakingThis", "unused")
abstract class BaseFragment<VM : BaseViewModel> : Fragment, ProgressInterface {

    protected open val viewModel: VM by viewModel(clazz = getViewModelClass())

    constructor() : super()

    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExceptions().observe(viewLifecycleOwner, ::showException)
        viewModel.getProgressLiveData().observe(viewLifecycleOwner) {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        }
        initializeLiveData()
    }

    protected open fun initializeLiveData() {
        // none
    }

    protected open fun showException(throwable: Throwable) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showException(throwable)
        } else {
            ToastDialog().setMessage(throwable.toString())
                .show(parentFragmentManager, 3)
        }
    }

    protected abstract fun getViewModelClass(): KClass<VM>
}