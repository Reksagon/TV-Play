package com.tevibox.tvplay.utils

import com.google.gson.Gson
import com.tevibox.tvplay.core.entity.DeviceInformation
import timber.log.Timber

object InfoUtils {

    fun getInfo(information: DeviceInformation): String {
//        val data = if (BuildConfig.DEBUG) {
//            "Android version: ${information.androidVersion}, Application version: ${information.appVersion}, Brand: ${information.brand}, Model: ${information.model}"
//        } else {
//            Gson().toJson(information)
//        }
        val data = Gson().toJson(information)
        Timber.d("getInfo: $data")
        return data
    }
}