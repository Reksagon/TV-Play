package com.tevibox.tvplay.di

import com.tevibox.tvplay.data.RetrofitDownloadFileManager
import com.tevibox.tvplay.domain.DownloadFileManager
import com.tevibox.tvplay.domain.TokenStore
import org.koin.dsl.module

val appModule = module {
    single { ActivityProvider(get()) }
    single { TokenStore(get()) }
    single<DownloadFileManager> {
//        DownloadFileManagerImpl(get())
        RetrofitDownloadFileManager(get(), get())
    }
    dataStoreModule()
    roomModule()
    retrofitModule()
    navigationModule()
    repositoryModule()
    interactorModule()
}