package com.tevibox.tvplay.presentation.viewmodel

import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class KeyEventSharedViewModel : BaseViewModel() {

    val keyEventLiveData = MutableLiveData<KeyEvent>()

    val returnKeyEventLiveData = MutableLiveData<Boolean>()

    fun getKeyEventLiveData(): LiveData<KeyEvent> = keyEventLiveData
}