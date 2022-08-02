package com.tevibox.tvplay

import android.app.Application
import com.tevibox.tvplay.di.appModule
import com.tevibox.tvplay.di.setupAndroidComponents
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TeviboxApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        createTimberTree()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@TeviboxApplication)
            modules(appModule)
            setupAndroidComponents()
        }
    }

    private fun createTimberTree() = Timber.plant(Timber.DebugTree())
}