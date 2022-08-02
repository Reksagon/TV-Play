package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.core.exception.InvalidTokenException
import com.tevibox.tvplay.domain.TokenStore
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import com.tevibox.tvplay.domain.repository.UserTokenRepository
import kotlinx.coroutines.flow.*

class RenewSavedTokenInteractor(
    private val userSettingsRepository: UserSettingsRepository,
    private val userTokenRepository: UserTokenRepository,
    private val getDeviceInformationInteractor: GetDeviceInformationInteractor,
    private val tokenStore: TokenStore
) : InteractorInterface<Unit, Flow<UserToken>> {

    override suspend fun invoke(param: Unit) = userSettingsRepository.get()
        .onEach { tokenStore.setToken(UserToken(it.tokenHash, it.tokenExpire, 0)) }
        .zip(getDeviceInformationInteractor(Unit)) { settings, deviceInformation ->
            val pair = settings to deviceInformation
            pair
        }.flatMapConcat { userTokenRepository.renew(it.first.tokenHash, it.second) }
        .onEach { tokenStore.setToken(it) }
        .catch { throw InvalidTokenException() }
}