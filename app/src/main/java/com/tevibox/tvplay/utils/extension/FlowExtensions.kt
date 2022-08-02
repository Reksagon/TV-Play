package com.tevibox.tvplay.utils.extension

import com.javavirys.core.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.onEach

fun <T> Flow<Result<T>>.onProgress(action: suspend (Result.Progress) -> Unit): Flow<Result<T>> {
    return onEach {
        if (it is Result.Progress) {
            action(it)
        }
    }
}

suspend fun <T> FlowCollector<Result<T>>.emitProgress(value: Int) =
    emit(Result.Progress(value))