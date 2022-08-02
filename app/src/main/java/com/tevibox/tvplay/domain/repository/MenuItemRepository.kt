package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuItemRepository {

    suspend fun getMenuItems(): Flow<List<MenuItem>>
}