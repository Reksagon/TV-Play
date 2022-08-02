package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.CurrentEpg
import com.tevibox.tvplay.core.entity.Stream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentProgramListInteractor : InteractorInterface<Stream, Flow<CurrentEpg>> {

    override suspend fun invoke(param: Stream) = flow {
        val currentTimeMillis = System.currentTimeMillis()
        param.epgList.forEachIndexed { index, currentProgram ->
            if (currentTimeMillis > currentProgram.start * 1000 && currentTimeMillis < currentProgram.stop * 1000) {
                val nextProgram = param.epgList.getOrNull(index + 1)
                val start = currentProgram.start * 1000
                var stop = currentProgram.stop * 1000
                val currentValue = if (stop == 0L) 0L else currentTimeMillis
                stop = if (stop == 0L) 1L else stop
                val progressValue = (100 * (currentValue - start) / (stop - start)).toInt()
                emit(CurrentEpg(currentProgram, nextProgram, progressValue))
                return@flow
            }
        }

        emit(CurrentEpg(null, null, 0))
    }
}