package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.Streams
import com.tevibox.tvplay.core.exception.CacheNotFoundException
import kotlinx.coroutines.flow.*

class GetCacheStreamsInteractor(
    private val getStreamsInteractor: GetStreamsInteractor
) : InteractorInterface<String, Flow<Streams>> {

    fun resetCache() = cacheMap.clear()

    override suspend fun invoke(param: String) = flow {
        val item = cacheMap[param] ?: throw CacheNotFoundException()
        if ((System.currentTimeMillis() - item.first) > CACHE_EXPIRE) throw CacheNotFoundException()
        emit(item.second)
    }.catch {
        emitAll(getStreamsInteractor(param))
    }.onEach {
        cacheMap[param] = System.currentTimeMillis() to it
    }

    companion object {
        private const val CACHE_EXPIRE = 3_600_000L // 10 min
        private val cacheMap = mutableMapOf<String, Pair<Long, Streams>>()
    }
}