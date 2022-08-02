package com.tevibox.tvplay.di

import androidx.room.Room
import com.tevibox.tvplay.data.room.AppDatabase
import org.koin.core.module.Module

fun Module.roomModule() {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, AppDatabase.NAME
        ).build()

//            .setQueryCallback(
//            { sqlQuery, bindArgs ->
//                Timber.d("SQL Query: $sqlQuery SQL Args: $bindArgs")
//            }, Executors.newSingleThreadExecutor()
//        )
    }

    single { get<AppDatabase>().getChannelDao() }
}