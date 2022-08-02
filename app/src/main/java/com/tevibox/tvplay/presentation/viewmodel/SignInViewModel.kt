package com.tevibox.tvplay.presentation.viewmodel

import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.domain.interactor.AuthUserInteractor
import com.tevibox.tvplay.domain.interactor.GetDeviceInformationInteractor
import com.tevibox.tvplay.presentation.navigation.Screens
import com.tevibox.tvplay.utils.InfoUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import timber.log.Timber

class SignInViewModel(
    private val router: Router,
    private val getDeviceInformationInteractor: GetDeviceInformationInteractor,
    private val authUserInteractor: AuthUserInteractor
) : BaseViewModel() {

    fun authUser(email: String, password: String) = launch {
        Timber.d("authUser email = $email, password = $password")
        getDeviceInformationInteractor(Unit).flatMapConcat {
            authUserInteractor(
                UserCredentials(
                    email,
                    password,
                    it.deviceId,
                    InfoUtils.getInfo(it)
                )
            )
        }.collect {
            Timber.d("Token: $it")
            router.replaceScreen(Screens.getMainScreen())
        }
    }

    fun navigateToSignUp() = router.navigateTo(Screens.getSignUpScreen())

    fun navigateToForgotPassword() = router.navigateTo(Screens.getForgotPasswordScreen())
}