package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun get(): Flow<Settings>
}