package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.domain.repository.StreamRepository

class AddStreamToDatabaseInteractor(
    private val streamRepository: StreamRepository,
    private val getCacheStreamsInteractor: GetCacheStreamsInteractor
) : InteractorInterface<Stream, Unit> {

    override suspend fun invoke(param: Stream) {
        getCacheStreamsInteractor.resetCache()
        streamRepository.add(param)
    }
}