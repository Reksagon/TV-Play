package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.domain.TokenStore

class LogoutInteractor(
    private val tokenStore: TokenStore
) : InteractorInterface<Unit, Unit> {

    override suspend fun invoke(param: Unit) = tokenStore.setToken(UserToken.buildEmptyToken())
}