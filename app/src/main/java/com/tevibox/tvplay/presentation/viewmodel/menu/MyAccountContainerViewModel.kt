package com.tevibox.tvplay.presentation.viewmodel.menu

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.core.entity.ActivationCode
import com.tevibox.tvplay.core.entity.User
import com.tevibox.tvplay.domain.interactor.LogoutInteractor
import com.tevibox.tvplay.domain.repository.ActivationCodeRepository
import com.tevibox.tvplay.domain.repository.UserRepository
import com.tevibox.tvplay.presentation.navigation.Screens
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect

class MyAccountContainerViewModel(
    private val router: Router,
    private val userRepository: UserRepository,
    private val activationCodeRepository: ActivationCodeRepository,
    private val logoutInteractor: LogoutInteractor
) : BaseViewModel() {

    val userLiveData = MutableLiveData<User>()

    fun loadInformation() = launchWithProgress {
        loadUserInformation()
    }

    fun activateSubscription(code: String) = launchWithProgress {
        activationCodeRepository.set(ActivationCode(code))
        loadUserInformation()
    }

    private suspend fun loadUserInformation() {
        userRepository.getCurrentUser()
            .collect {
                userLiveData.postValue(it)
            }
    }

    fun logout() = launch {
        logoutInteractor(Unit)
        router.newRootChain(Screens.getLoginScreen())
    }
}