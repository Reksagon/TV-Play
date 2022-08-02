package com.tevibox.tvplay.domain

import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.core.exception.InvalidDeviceInformationException
import com.tevibox.tvplay.core.exception.InvalidTokenException
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.single
import timber.log.Timber

class TokenStore(
    private val userSettingsRepository: UserSettingsRepository
) {

    var deviceInformation: DeviceInformation? = null

    suspend fun setToken(token: UserToken?) {
        val settings = userSettingsRepository.get().single()
        val newSettings = settings.copy(
            tokenHash = token?.hash ?: "",
            tokenExpire = token?.expire ?: 0L
        )
        userSettingsRepository.set(newSettings)
    }

    suspend fun getToken(): UserToken {
        val settings = userSettingsRepository.get().single()
        if (settings.tokenHash.isEmpty() || settings.tokenExpire == 0L)
            throw InvalidTokenException()

        return UserToken(settings.tokenHash, settings.tokenExpire, settings.tokenTime)
    }

    suspend fun isTokenExpired(): Boolean {
        val token = getToken()
        val expire = token.expire
        val isExpired = (expire <= (System.currentTimeMillis() / 1000))
        Timber.d("isTokenExpired(): $isExpired")
        return isExpired
    }

    fun requireDeviceInformation(): DeviceInformation {
        if (deviceInformation == null) throw InvalidDeviceInformationException()
        return deviceInformation!!
    }
}