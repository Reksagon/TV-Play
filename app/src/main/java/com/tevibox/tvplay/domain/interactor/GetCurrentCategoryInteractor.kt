package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.domain.repository.SettingsRepository
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class GetCurrentCategoryInteractor(
    private val settingsRepository: SettingsRepository,
    private val userSettingsRepository: UserSettingsRepository
) : InteractorInterface<Unit, Flow<Category>> {

    override suspend fun invoke(param: Unit) = settingsRepository.get()
        .map { it.categories }
        .zip(userSettingsRepository.get()) { categories, userSettings ->
            categories.find { it.name == userSettings.categoryName } ?: categories[0]
        }
}