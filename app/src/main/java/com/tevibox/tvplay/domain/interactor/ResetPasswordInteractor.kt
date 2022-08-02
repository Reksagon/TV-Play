package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.exception.InvalidEmailException
import com.tevibox.tvplay.core.exception.ResponseException
import com.tevibox.tvplay.data.retrofit.UnAuthApiService
import com.tevibox.tvplay.utils.StringUtils

class ResetPasswordInteractor(
    private val authApiService: UnAuthApiService
) : InteractorInterface<String, Unit> {

    override suspend fun invoke(param: String) {
        if (!StringUtils.isValidEmail(param)) throw InvalidEmailException()
        val networkBaseResponse = authApiService.resetPassword(param)
        if (networkBaseResponse.error != 0) {
            throw ResponseException(networkBaseResponse.error, networkBaseResponse.msg)
        }
    }
}