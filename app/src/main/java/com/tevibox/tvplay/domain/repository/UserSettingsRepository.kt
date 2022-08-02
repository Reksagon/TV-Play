package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    suspend fun get(): Flow<UserSettings>

    suspend fun set(settings: UserSettings)
}