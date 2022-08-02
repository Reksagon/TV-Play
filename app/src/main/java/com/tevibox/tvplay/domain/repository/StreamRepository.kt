package com.tevibox.tvplay.domain.repository

import com.tevibox.tvplay.core.entity.Stream
import kotlinx.coroutines.flow.Flow

interface StreamRepository {

    suspend fun getAll(): Flow<List<Stream>>

    suspend fun add(stream: Stream)
}