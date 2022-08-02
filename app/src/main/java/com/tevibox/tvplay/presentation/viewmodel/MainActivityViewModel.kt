package com.tevibox.tvplay.presentation.viewmodel

import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.presentation.navigation.Screens

class MainActivityViewModel(
    private val router: Router
) : BaseViewModel() {

    fun navigateToMainScreen() {
        router.newRootScreen(Screens.getPlayerScreen())
    }
}