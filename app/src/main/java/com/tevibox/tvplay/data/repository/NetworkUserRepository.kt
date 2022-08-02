package com.tevibox.tvplay.data.repository

import com.tevibox.tvplay.data.mapper.NetworkUserToUserMapper
import com.tevibox.tvplay.data.retrofit.AuthApiService
import com.tevibox.tvplay.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NetworkUserRepository(
    private val authApiService: AuthApiService
) : UserRepository {

    override suspend fun getCurrentUser() = flow {
        emit(authApiService.getCurrentUserInfo())
    }.map(NetworkUserToUserMapper()::transform)
}