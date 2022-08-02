package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.domain.repository.StreamRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteStreamListInteractor(
    private val streamRepository: StreamRepository
) : InteractorInterface<Unit, Flow<List<Stream>>> {

    override suspend fun invoke(param: Unit) = streamRepository.getAll()
}