package com.tevibox.tvplay.data.repository

import android.content.Context
import com.tevibox.tvplay.data.mapper.NetworkSettingsToSettingsMapper
import com.tevibox.tvplay.data.retrofit.AuthApiService
import com.tevibox.tvplay.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NetworkSettingsRepository(
    private val context: Context,
    private val authApiService: AuthApiService
) : SettingsRepository {

    override suspend fun get() = flow {
        emit(authApiService.getSettings())
    }.map(NetworkSettingsToSettingsMapper(context)::transform)
}