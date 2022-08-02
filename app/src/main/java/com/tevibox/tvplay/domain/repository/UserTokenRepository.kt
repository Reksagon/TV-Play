package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.core.entity.UserToken
import kotlinx.coroutines.flow.Flow

interface UserTokenRepository {

    suspend fun getNew(userCredentials: UserCredentials): Flow<UserToken>

    suspend fun get(userCredentials: UserCredentials): Flow<UserToken>

    suspend fun renew(hash: String, deviceInformation: DeviceInformation): Flow<UserToken>
}