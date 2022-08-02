package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.data.retrofit.AuthApiService

class KeepAliveInteractor(
    private val authApiService: AuthApiService
) : InteractorInterface<Unit, Unit> {

    override suspend fun invoke(param: Unit) = authApiService.keepAlive()
}