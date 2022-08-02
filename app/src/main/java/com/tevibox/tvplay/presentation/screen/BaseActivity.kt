package com.tevibox.tvplay.presentation.screen

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.tevibox.tvplay.R
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

@Suppress("LeakingThis")
abstract class BaseActivity<VM : BaseViewModel> : ScopeActivity, ProgressInterface {

    protected open val viewModel: VM by viewModel(clazz = getViewModelClass())

    private val navigator: Navigator by inject { parametersOf(this) }

    private val navigatorHolder: NavigatorHolder by inject()

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getExceptions().observe(this, ::showException)
        viewModel.getProgressLiveData().observe(this) {
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

    open fun showException(throwable: Throwable) {
        when (throwable) {
            is UnknownHostException -> showToastMessage(R.string.no_internet_connection)
            is SocketTimeoutException -> showToastMessage(R.string.server_unavailable)
            else -> showToastMessage(throwable.toString())
        }
    }

    private fun showToastMessage(@StringRes messageId: Int) {
        ToastDialog().setMessage(messageId)
            .show(supportFragmentManager, 3)
    }

    private fun showToastMessage(message: String) {
        ToastDialog().setMessage(message)
            .show(supportFragmentManager, 3)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    protected abstract fun getViewModelClass(): KClass<VM>
}