package com.tevibox.tvplay.data.repository

import com.tevibox.tvplay.core.entity.ActivationCode
import com.tevibox.tvplay.data.retrofit.AuthApiService
import com.tevibox.tvplay.domain.repository.ActivationCodeRepository

class NetworkActivationCodeRepository(
    private val authApiService: AuthApiService
) : ActivationCodeRepository {

    override suspend fun set(activationCode: ActivationCode) {
        authApiService.activate(activationCode.code)
    }
}