package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.core.entity.Streams
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {

    suspend fun getStreams(category: String): Flow<Streams>

    suspend fun setActiveStream(stream: Stream)
}