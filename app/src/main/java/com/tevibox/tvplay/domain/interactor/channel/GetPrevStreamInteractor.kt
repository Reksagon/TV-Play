package com.tevibox.tvplay.domain.interactor.channel

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.CategoryWithStream
import com.tevibox.tvplay.domain.interactor.GetCacheStreamsInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPrevStreamInteractor(
    private val getCacheStreamsInteractor: GetCacheStreamsInteractor
) : InteractorInterface<CategoryWithStream, Flow<CategoryWithStream>> {

    override suspend fun invoke(param: CategoryWithStream) = getCacheStreamsInteractor(
        param.category.name
    ).map {
        param.stream.selected = false
        val stream = it.streams.getOrElse(param.stream.number - 2) { _ ->
            it.streams.last()
        }
        param.copy(stream = stream)
    }
}