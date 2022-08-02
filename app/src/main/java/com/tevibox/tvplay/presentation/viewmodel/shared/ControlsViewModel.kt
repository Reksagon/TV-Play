package com.tevibox.tvplay.presentation.viewmodel.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tevibox.tvplay.core.entity.PlayerStateEnum
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import com.tevibox.tvplay.utils.extension.toLiveData

class ControlsViewModel : BaseViewModel() {

    private val leftMenuLayoutFlagLiveData = MutableLiveData<Boolean>()

    private val selectItemLeftMenuLiveData = MutableLiveData<Int>()

    private val categoriesMenuFlagLiveData = MutableLiveData<Boolean>()

    private val hideMenuFlagLiveData = MutableLiveData<Unit>()

    private val epgMenuFlag = MutableLiveData<Boolean>()

    private val isChannelContextMenuShowed = MutableLiveData<Boolean>()

    private val playerControl = MutableLiveData<PlayerStateEnum>()

    fun getShowLeftMenuLayoutFlag(): LiveData<Boolean> = leftMenuLayoutFlagLiveData

    fun setShowLeftMenuLayoutFlag(flag: Boolean) {
        leftMenuLayoutFlagLiveData.value = flag
    }

    fun getHideMenuFlag(): LiveData<Unit> = hideMenuFlagLiveData

    fun setHideMenuFlag() {
        hideMenuFlagLiveData.value = Unit
    }

    fun getShowEpgMenuFlag(): LiveData<Boolean> = epgMenuFlag

    fun showEpgMenu(flag: Boolean) {
        epgMenuFlag.value = flag
    }

    fun getChannelContextMenuFlag(): LiveData<Boolean> = isChannelContextMenuShowed

    fun setShowChannelContextMenuFlag(flag: Boolean) {
        isChannelContextMenuShowed.value = flag
    }

    fun getSelectedItemLeftMenuLiveData(): LiveData<Int> = selectItemLeftMenuLiveData

    fun setSelectedItemLeftMenuLiveData(index: Int) {
        selectItemLeftMenuLiveData.value = index
    }

    fun getShowCategoriesMenuFlag(): LiveData<Boolean> = categoriesMenuFlagLiveData

    fun showCategoriesMenu(flag: Boolean) {
        categoriesMenuFlagLiveData.value = flag
    }

    fun getPlayerControlState() = playerControl.toLiveData()

    fun setPlayerState(state: PlayerStateEnum) {
        playerControl.value = state
    }
}