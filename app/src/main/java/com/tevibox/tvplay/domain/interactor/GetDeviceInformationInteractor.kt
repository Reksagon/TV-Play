package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.domain.TokenStore
import com.tevibox.tvplay.domain.repository.DeviceInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetDeviceInformationInteractor(
    private val deviceInformationRepository: DeviceInformationRepository,
    private val tokenStore: TokenStore
) : InteractorInterface<Unit, Flow<DeviceInformation>> {

    override suspend fun invoke(param: Unit) = deviceInformationRepository.get()
        .onEach { tokenStore.deviceInformation = it }
}