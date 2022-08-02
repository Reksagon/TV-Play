package com.tevibox.tvplay.presentation.viewmodel.menu

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.domain.repository.MenuItemRepository
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MenuViewModel(
    private val router: Router,
    private val menuItemRepository: MenuItemRepository,
) : BaseViewModel() {

    val menuItemsLiveData = MutableLiveData<List<MenuItem>>()

    fun loadInformation() = launch {
        menuItemRepository.getMenuItems()
            .collect {
                Timber.d("MenuItems: $it")
                menuItemsLiveData.postValue(it)
            }
    }
}