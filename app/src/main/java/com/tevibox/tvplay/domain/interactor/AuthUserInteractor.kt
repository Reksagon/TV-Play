package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.domain.TokenStore
import com.tevibox.tvplay.domain.repository.UserTokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach

class AuthUserInteractor(
    private val validateUserCredentialsInteractor: ValidateUserCredentialsInteractor,
    private val userTokenRepository: UserTokenRepository,
    private val tokenStore: TokenStore
) : InteractorInterface<UserCredentials, Flow<UserToken>> {

    override suspend fun invoke(param: UserCredentials): Flow<UserToken> =
        validateUserCredentialsInteractor(param).flatMapConcat(userTokenRepository::get)
            .onEach { tokenStore.setToken(it) }
}