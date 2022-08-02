package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getCurrentUser(): Flow<User>
}