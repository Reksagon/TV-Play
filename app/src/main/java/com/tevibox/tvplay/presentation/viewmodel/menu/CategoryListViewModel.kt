package com.tevibox.tvplay.presentation.viewmodel.menu

import androidx.lifecycle.MutableLiveData
import com.tevibox.tvplay.core.entity.Settings
import com.tevibox.tvplay.domain.repository.SettingsRepository
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect

class CategoryListViewModel(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    val settingsLiveData = MutableLiveData<Settings>()

    fun loadCategories() = launch {
        settingsRepository.get()
            .collect { settingsLiveData.postValue(it) }
    }
}