package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.DeviceInformation
import kotlinx.coroutines.flow.Flow

interface DeviceInformationRepository {

    fun get(): Flow<DeviceInformation>
}