package com.tevibox.tvplay.presentation.viewmodel.shared

import androidx.lifecycle.MutableLiveData
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import com.tevibox.tvplay.utils.extension.toLiveData

class TvViewModel : BaseViewModel() {

    private val playingStreamLiveData = MutableLiveData<Stream>()

    private val currentCategoryLiveData = MutableLiveData<Category>()

    private val selectCategoryLiveData = MutableLiveData<Category>()

    private val channelRequestFocusLiveData = MutableLiveData<Boolean>()

    fun getCurrentCategory() = currentCategoryLiveData.toLiveData()

    fun selectCurrentCategory(category: Category) {
        currentCategoryLiveData.value = category
    }

    fun getPlayingStreamLiveData() = playingStreamLiveData.toLiveData()

    fun selectPlayingStream(stream: Stream) {
        playingStreamLiveData.value = stream
    }

    fun getSelectedCategory() = selectCategoryLiveData.toLiveData()

    fun selectCategory(category: Category) {
        selectCategoryLiveData.value = category
    }

    fun getFocusOnSelectedChannelFlag() = channelRequestFocusLiveData.toLiveData()

    fun focusOnSelectedChannel(flag: Boolean) {
        channelRequestFocusLiveData.value = flag
    }
}