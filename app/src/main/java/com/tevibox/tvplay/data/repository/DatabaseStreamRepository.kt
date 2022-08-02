package com.tevibox.tvplay.data.repository

import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.room.dao.ChannelDao
import com.tevibox.tvplay.data.room.mapper.ChannelDboListToStreamListMapper
import com.tevibox.tvplay.data.room.mapper.StreamToChannelDboMapper
import com.tevibox.tvplay.domain.repository.StreamRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DatabaseStreamRepository(
    private val channelDao: ChannelDao
) : StreamRepository {

    override suspend fun getAll() = flow {
        val channels = channelDao.getChannels()
        emit(channels)
    }.map(ChannelDboListToStreamListMapper()::transform)

    override suspend fun add(stream: Stream) =
        channelDao.add(StreamToChannelDboMapper().transform(stream))
}