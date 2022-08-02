package com.tevibox.tvplay.di

import com.tevibox.tvplay.data.repository.*
import com.tevibox.tvplay.domain.repository.*
import org.koin.core.module.Module

const val DATABASE_NAMED = "DATABASE_NAMED"
const val LOCAL_NAMED = "LOCAL_NAMED"
const val NETWORK_NAMED = "NETWORK_NAMED"

fun Module.repositoryModule() {

    single<UserTokenRepository> { NetworkUserTokenRepository(get()) }

    single<DeviceInformationRepository> { LocalDeviceInformationRepository(get()) }

    single<StreamsRepository> { NetworkStreamsRepository(get(), get()) }

    single<MenuItemRepository> { LocalMenuItemRepository() }

    single<SettingsRepository> { NetworkSettingsRepository(get(), get()) }

    single<UserRepository> { NetworkUserRepository(get()) }

    single<ActivationCodeRepository> { NetworkActivationCodeRepository(get()) }

    single<StreamRepository> { DatabaseStreamRepository(get()) }

    single<UserSettingsRepository> { PreferencesUserSettingsRepository(get()) }
}