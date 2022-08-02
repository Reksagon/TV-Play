package com.tevibox.tvplay.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel() {

    private val exceptionLiveData = MutableLiveData<Throwable>()

    private val defaultExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onException(throwable)
    }

    protected val progressLiveData = MutableLiveData<Boolean>()

    protected open fun onException(throwable: Throwable) {
        viewModelScope.launch {
            hideProgress()
            throwable.printStackTrace()
            exceptionLiveData.postValue(throwable)
        }
    }

    protected fun launchWithProgress(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        runnable: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(defaultExceptionHandler) {
            withContext(Dispatchers.Main) { progressLiveData.value = true }
            withContext(coroutineContext, runnable)
            hideProgress()
        }
    }

    private suspend fun hideProgress() {
        withContext(Dispatchers.Main) { progressLiveData.value = false }
    }

    protected fun launch(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        runnable: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(defaultExceptionHandler) {
            withContext(coroutineContext, runnable)
        }
    }

    protected fun launchWithReturnJob(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        runnable: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(defaultExceptionHandler) {
        withContext(coroutineContext, runnable)
    }

    fun getExceptions(): LiveData<Throwable> = exceptionLiveData

    fun getProgressLiveData(): LiveData<Boolean> = progressLiveData
}