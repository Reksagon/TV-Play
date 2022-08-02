package com.tevibox.tvplay.di

import com.tevibox.tvplay.presentation.viewmodel.*
import com.tevibox.tvplay.presentation.viewmodel.menu.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { MainActivityViewModel(get()) }
    viewModel {
        PlayerViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { SignUpViewModel(get(), get(), get()) }
    viewModel { SignInViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ResetPasswordViewModel(get(), get()) }

    // Menu
    viewModel { MenuViewModel(get(), get()) }

    // TV Channels
    viewModel { TvProgramSelectorViewModel() }
    viewModel { CategoryListViewModel(get()) }
    viewModel { ChannelListViewModel(get(), get(), get(), get()) }
    viewModel { EpgListViewModel() }

    viewModel { FavoriteViewModel() }

    // My Account
    viewModel { MyAccountContainerViewModel(get(), get(), get(), get()) }

    // Info
    viewModel { InfoContainerViewModel(get(), get(), get(), get()) }
}