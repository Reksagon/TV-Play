package com.tevibox.tvplay.data.repository

import com.tevibox.tvplay.core.entity.MenuEnum
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.domain.repository.MenuItemRepository
import kotlinx.coroutines.flow.flow

class LocalMenuItemRepository : MenuItemRepository {

    override suspend fun getMenuItems() = flow {
        val menuItemList = MenuEnum.values()
            .map { MenuItem(it.ordinal, it.iconId, it.titleId) }
        emit(menuItemList)
    }
}