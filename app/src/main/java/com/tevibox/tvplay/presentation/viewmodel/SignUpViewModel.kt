package com.tevibox.tvplay.presentation.viewmodel

import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.domain.interactor.GetDeviceInformationInteractor
import com.tevibox.tvplay.domain.interactor.RegisterUserInteractor
import com.tevibox.tvplay.presentation.navigation.Screens
import com.tevibox.tvplay.utils.InfoUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import timber.log.Timber

class SignUpViewModel(
    private val router: Router,
    private val registerUserInteractor: RegisterUserInteractor,
    private val getDeviceInformationInteractor: GetDeviceInformationInteractor
) : BaseViewModel() {

    fun registerUser(email: String, password: String, confirmPassword: String) = launch {
        Timber.d("registerUser email = $email, password = $password, confirmPassword = $confirmPassword")
        getDeviceInformationInteractor(Unit).flatMapConcat {
            registerUserInteractor(
                UserCredentials(
                    email,
                    password,
                    it.deviceId,
                    InfoUtils.getInfo(it),
                    confirmPassword
                )
            )
        }.collect {
            Timber.d("Token: $it")
            router.replaceScreen(Screens.getMainScreen())
        }
    }

    fun navigateToBack() = router.exit()
}