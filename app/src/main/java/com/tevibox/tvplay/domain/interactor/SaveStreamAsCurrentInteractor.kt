package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.CategoryWithStream
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.collect

class SaveStreamAsCurrentInteractor(
    private val userSettingsRepository: UserSettingsRepository
) : InteractorInterface<CategoryWithStream, Unit> {

    override suspend fun invoke(param: CategoryWithStream) {
        userSettingsRepository.get().collect {
            val newSettings = it.copy(
                categoryName = param.category.name,
                channelName = param.stream.name,
                channelUrl = param.stream.url
            )
            userSettingsRepository.set(newSettings)
        }
    }
}