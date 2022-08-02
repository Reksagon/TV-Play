package com.tevibox.tvplay.di

import com.github.terrakok.cicerone.Navigator
import com.tevibox.tvplay.presentation.navigation.LoginActivityNavigator
import com.tevibox.tvplay.presentation.screen.LoginActivity
import org.koin.dsl.module

val loginActivityModule = module {

    scope<LoginActivity> {
        scoped<Navigator> { LoginActivityNavigator(get()) }
    }
}