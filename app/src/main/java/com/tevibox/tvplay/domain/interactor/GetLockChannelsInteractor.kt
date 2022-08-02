package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Streams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLockChannelsInteractor(
    private val getStreamsInteractor: GetCacheStreamsInteractor
) : InteractorInterface<Unit, Flow<Streams>> {

    override suspend fun invoke(param: Unit) = getStreamsInteractor("")
        .map { streams ->
            val list = streams.streams.filter { it.lock }
            Streams(0, list, System.currentTimeMillis())
        }
}