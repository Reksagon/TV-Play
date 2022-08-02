package com.tevibox.tvplay.data

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.javavirys.core.entity.Result
import com.javavirys.core.extension.emitSuccess
import com.tevibox.tvplay.data.retrofit.UnAuthApiService
import com.tevibox.tvplay.domain.DownloadFileManager
import com.tevibox.tvplay.utils.extension.emitProgress
import com.tevibox.tvplay.utils.extension.getUriForString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class RetrofitDownloadFileManager(
    private val context: Context,
    private val unAuthApiService: UnAuthApiService
) : DownloadFileManager {

    override fun initialize() {
    }

    override fun release() {
        Timber.d("release")
    }

    override suspend fun loadFile(
        url: String,
        title: String,
        description: String,
        name: String
    ): Flow<Result<Uri>> = flow {
        Timber.d("loadFile")
        val responseBody = unAuthApiService.downloadFile(url)
        val contentLength = responseBody.contentLength()
        val byteStream = responseBody.byteStream()

        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name)
        Timber.d("FilePath: $file")
        if (file.exists()) {
            file.delete()
        }

        byteStream.use { input ->
            file.outputStream().use { output ->
                mapInputStreamToOutputStream(input, output) {
                    val progress =
                        if (contentLength == 0L) 0 else ((it * 100) / contentLength).toInt()
                    emitProgress(progress)
                }
            }
        }
        val providerUri = context.getUriForString(file.path)
        emitSuccess(providerUri)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun mapInputStreamToOutputStream(
        input: InputStream,
        out: OutputStream,
        bufferSize: Int = DEFAULT_BUFFER_SIZE,
        action: suspend (Long) -> Unit
    ): Long {
        Timber.d("mapInputStreamToOutputStream")

        var bytesCopied: Long = 0
        val buffer = ByteArray(bufferSize)
        var bytes = input.read(buffer)

        while (bytes >= 0) {
            out.write(buffer, 0, bytes)
            bytesCopied += bytes
            bytes = input.read(buffer)
            action(bytesCopied)
        }
        return bytesCopied
    }
}