package com.tevibox.tvplay.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.Module

fun Module.navigationModule() {
    single { Cicerone.create(Router()) }
    factory { get<Cicerone<Router>>().router }
    factory { get<Cicerone<Router>>().getNavigatorHolder() }
}