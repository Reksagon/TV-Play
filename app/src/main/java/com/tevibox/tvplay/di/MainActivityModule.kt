package com.tevibox.tvplay.di

import com.github.terrakok.cicerone.Navigator
import com.tevibox.tvplay.presentation.navigation.MainActivityNavigator
import com.tevibox.tvplay.presentation.screen.MainActivity
import org.koin.dsl.module

val mainActivityModule = module {

    scope<MainActivity> {
        scoped<Navigator> { MainActivityNavigator(get()) }
    }
}