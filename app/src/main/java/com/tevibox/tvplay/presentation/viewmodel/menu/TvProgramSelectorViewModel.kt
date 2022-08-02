package com.tevibox.tvplay.presentation.viewmodel.menu

import androidx.lifecycle.MutableLiveData
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class TvProgramSelectorViewModel : BaseViewModel() {

    val timerLiveData = MutableLiveData<Unit>()

    var timerJob: Job? = null

    fun startTimer(delay: Long = DELAY) {
        if (timerJob == null) {
            timerJob = launchWithReturnJob {
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < delay) {
                    delay(1000)
                }
                timerLiveData.postValue(Unit)
            }
        }
    }

    fun killTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun restartTimer(delay: Long = DELAY) {
        if (timerJob != null) {
            killTimer()
        }
        startTimer(delay)
    }

    companion object {
        private const val DELAY = 5000L // 5 sec
    }
}