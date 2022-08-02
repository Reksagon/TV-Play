package com.tevibox.tvplay.presentation.viewmodel.menu

import androidx.lifecycle.MutableLiveData
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.core.entity.Streams
import com.tevibox.tvplay.domain.interactor.AddStreamToDatabaseInteractor
import com.tevibox.tvplay.domain.interactor.GetCacheStreamsInteractor
import com.tevibox.tvplay.domain.interactor.GetFavoriteStreamsInteractor
import com.tevibox.tvplay.domain.interactor.GetLockChannelsInteractor
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class ChannelListViewModel(
    private val getStreamsInteractor: GetCacheStreamsInteractor,
    private val getFavoriteStreamsInteractor: GetFavoriteStreamsInteractor,
    private val addStreamToDatabaseInteractor: AddStreamToDatabaseInteractor,
    private val getLockChannelsInteractor: GetLockChannelsInteractor
) : BaseViewModel() {

    val streamsLiveData = MutableLiveData<Streams>()

    fun loadChannels(category: String) = launch {
        getStreamsInteractor(category)
            .collect { streamsLiveData.postValue(it) }
    }

    fun addOrRemoveFavoriteChannel(item: Stream) = launch {
        addStreamToDatabaseInteractor(item)
    }

    fun lockOrUnlockChannel(item: Stream) = launch {
        Timber.d("lockOrUnlockChannel: $item")
        addStreamToDatabaseInteractor(item)
    }

    fun loadFilters() = launch {
        getFavoriteStreamsInteractor(Unit).collect {
            streamsLiveData.postValue(it)
        }
    }

    fun loadLockChannels() = launch {
        getLockChannelsInteractor(Unit).collect {
            streamsLiveData.postValue(it)
        }
    }
}