package com.tevibox.tvplay.data.repository

import android.content.Context
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.mapper.NetworkStreamsToStreamsMapper
import com.tevibox.tvplay.data.retrofit.AuthApiService
import com.tevibox.tvplay.domain.repository.StreamsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NetworkStreamsRepository(
    private val context: Context,
    private val authApiService: AuthApiService
) : StreamsRepository {

    override suspend fun getStreams(category: String) = flow {
//        authApiService.activate("1")
        val streams = if (category.isEmpty() || category == context.getString(R.string.all)) {
            authApiService.getStreams()
        } else {
            authApiService.getStreams(category)
        }
        emit(streams)
    }.map(NetworkStreamsToStreamsMapper()::transform)

    override suspend fun setActiveStream(stream: Stream) = authApiService.view(stream.id)
}