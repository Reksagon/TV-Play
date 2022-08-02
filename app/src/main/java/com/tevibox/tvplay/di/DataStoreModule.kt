package com.tevibox.tvplay.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.module.Module

private const val PREFERENCES_NAME = "preferences"

fun Module.dataStoreModule() {

    single<SharedPreferences> {
        get<Context>().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}