package com.tevibox.tvplay.domain

import android.net.Uri
import com.javavirys.core.entity.Result
import kotlinx.coroutines.flow.Flow

interface DownloadFileManager {

    fun initialize()

    fun release()

    suspend fun loadFile(
        url: String,
        title: String,
        description: String,
        name: String
    ): Flow<Result<Uri>>
}