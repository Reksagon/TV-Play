package com.tevibox.tvplay.presentation.viewmodel

import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.domain.interactor.AuthUserInteractor
import com.tevibox.tvplay.domain.interactor.GetDeviceInformationInteractor
import com.tevibox.tvplay.presentation.navigation.Screens
import com.tevibox.tvplay.utils.InfoUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat

class LoginViewModel(
    private val router: Router,
    private val getDeviceInformationInteractor: GetDeviceInformationInteractor,
    private val authUserInteractor: AuthUserInteractor
) : BaseViewModel() {

    fun navigateToNextScreen() = launch {
        getDeviceInformationInteractor(Unit).flatMapConcat {
            authUserInteractor(
                UserCredentials(
                    device = it.deviceId,
                    info = InfoUtils.getInfo(it)
                )
            )
        }.collect {
            router.newRootChain(Screens.getMainScreen())
        }
    }
}