package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.ActivationCode

interface ActivationCodeRepository {

    suspend fun set(activationCode: ActivationCode)
}