package com.tevibox.tvplay.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.domain.repository.DeviceInformationRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class LocalDeviceInformationRepository(
    private val context: Context
) : DeviceInformationRepository {

    @SuppressLint("HardwareIds")
    override fun get() = flow {
        val deviceInformation = if (BuildConfig.DEBUG) {
            DeviceInformation(
                "9452207416528635",
                31,
                1,
                "google",
                "AOSP TV on x86",
                "STT9.210920.002 dev-keys"
            )
        } else {
            DeviceInformation(
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                Build.VERSION.SDK_INT,
                BuildConfig.VERSION_CODE,
                Build.BRAND,
                Build.MODEL,
                Build.DISPLAY
            )
        }
        Timber.d("DeviceInformation: $deviceInformation")
        emit(deviceInformation)
    }
}