package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.core.exception.StreamNotFoundException
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip

class GetCurrentStreamInteractor(
    private val getStreamsInteractor: GetCacheStreamsInteractor,
    private val userSettingsRepository: UserSettingsRepository
) : InteractorInterface<String, Flow<Stream>> {

    override suspend fun invoke(param: String) = getStreamsInteractor(param)
        .zip(userSettingsRepository.get()) { streams, userSettings ->
            streams.streams.find {
                it.name == userSettings.channelName && it.url == userSettings.channelUrl
            } ?: throw StreamNotFoundException()
        }.catch {
            val streams = getStreamsInteractor("").single()
            emit(streams.streams[0])
        }
}