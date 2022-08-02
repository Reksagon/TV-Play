package com.tevibox.tvplay.di

import org.koin.core.KoinApplication

fun KoinApplication.setupAndroidComponents() {
    setupActivities()
    setupFragments()
}

private fun KoinApplication.setupActivities() = modules(
    splashActivityModule,
    mainActivityModule,
    loginActivityModule
)

private fun KoinApplication.setupFragments() = modules(
    viewModelsModule
)