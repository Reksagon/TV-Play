package com.tevibox.tvplay.data.repository

import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.data.mapper.NetworkTokenToUserTokenMapper
import com.tevibox.tvplay.data.retrofit.UnAuthApiService
import com.tevibox.tvplay.domain.repository.UserTokenRepository
import com.tevibox.tvplay.utils.InfoUtils
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NetworkUserTokenRepository(
    private val unAuthApiService: UnAuthApiService
) : UserTokenRepository {

    override suspend fun getNew(userCredentials: UserCredentials) = flow {
        emit(
            unAuthApiService.registerSubscriber(
                userCredentials.email,
                userCredentials.password,
                userCredentials.device,
                userCredentials.info
            )
        )
    }.map(NetworkTokenToUserTokenMapper()::transform)

    override suspend fun get(userCredentials: UserCredentials) = flow {
        emit(
            unAuthApiService.authSubscriber(
                userCredentials.device,
                userCredentials.info
            )
        )
    }.map(NetworkTokenToUserTokenMapper()::transform)

    override suspend fun renew(hash: String, deviceInformation: DeviceInformation) = flow {
        emit(
            unAuthApiService.updateToken(
                hash,
                deviceInformation.deviceId,
                InfoUtils.getInfo(deviceInformation)
            )
        )
    }.map(NetworkTokenToUserTokenMapper()::transform)
}