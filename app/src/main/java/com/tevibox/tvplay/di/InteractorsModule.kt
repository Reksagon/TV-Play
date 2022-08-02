package com.tevibox.tvplay.di

import com.tevibox.tvplay.domain.interactor.*
import com.tevibox.tvplay.domain.interactor.channel.GetNextStreamInteractor
import com.tevibox.tvplay.domain.interactor.channel.GetPrevStreamInteractor
import com.tevibox.tvplay.domain.interactor.validator.SignUpValidateUserCredentialsInteractor
import org.koin.core.module.Module

fun Module.interactorModule() {

    // Validators
    single { ValidateUserCredentialsInteractor() }
    single { SignUpValidateUserCredentialsInteractor() }

    // Login
    single { RegisterUserInteractor(get(), get(), get()) }

    single { AuthUserInteractor(get(), get(), get()) }

    single { GetDeviceInformationInteractor(get(), get()) }

    single { LogoutInteractor(get()) }

    single { KeepAliveInteractor(get()) }

    // Streams
    single { GetFavoriteStreamListInteractor(get()) }

    single { GetFavoriteStreamsInteractor(get()) }

    single { GetStreamsInteractor(get(), get()) }

    single { GetLockChannelsInteractor(get()) }

    single { AddStreamToDatabaseInteractor(get(), get()) }

    single { GetCurrentCategoryInteractor(get(), get()) }

    single { GetCurrentStreamInteractor(get(), get()) }

    single { GetCurrentCategoryWithStreamInteractor(get(), get(), get()) }

    single { SaveStreamAsCurrentInteractor(get()) }

    single { RenewSavedTokenInteractor(get(), get(), get(), get()) }

    single { GetCacheStreamsInteractor(get()) }

    single { GetNextStreamInteractor(get()) }

    single { GetPrevStreamInteractor(get()) }

    single { GetCurrentProgramListInteractor() }

    single { ResetPasswordInteractor(get()) }
}