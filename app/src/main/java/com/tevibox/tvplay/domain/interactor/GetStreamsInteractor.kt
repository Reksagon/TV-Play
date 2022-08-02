package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Streams
import com.tevibox.tvplay.domain.repository.StreamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class GetStreamsInteractor(
    private val streamsRepository: StreamsRepository,
    private val getFavoriteStreamListInteractor: GetFavoriteStreamListInteractor
) : InteractorInterface<String, Flow<Streams>> {

    override suspend fun invoke(param: String) = streamsRepository.getStreams(param)
        .zip(getFavoriteStreamListInteractor(Unit)) { streams, favoriteList ->
            favoriteList.forEach { favoriteStream ->
                streams.streams.find {
                    favoriteStream.name == it.name && favoriteStream.logo == it.logo
                }?.let {
                    it.favorite = favoriteStream.favorite
                    it.lock = favoriteStream.lock
                    it.password = favoriteStream.password
                }
            }

            streams
        }
}