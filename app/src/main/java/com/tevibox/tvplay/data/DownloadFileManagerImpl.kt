package com.tevibox.tvplay.data

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.javavirys.core.entity.Result
import com.javavirys.core.extension.emitError
import com.javavirys.core.extension.emitSuccess
import com.tevibox.tvplay.domain.DownloadFileManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class DownloadFileManagerImpl(
    private val context: Context
) : DownloadFileManager {

    private var downloadManager: DownloadManager? = null

    private var referenceId = 0L

    override fun initialize() {
        Timber.d("initialize")
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
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
        try {
            val uri = Uri.parse(url)

            referenceId = downloadManager?.enqueue(
                DownloadManager.Request(uri)
                    .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                    ).setAllowedOverRoaming(false)
                    .setTitle(title)
                    .setDescription(description)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
            ) ?: 0L

            updateProgress(this)

            val providerUri = downloadManager?.getUriForDownloadedFile(referenceId)
            Timber.d("providerUri = $providerUri")
            emitSuccess(providerUri!!)

        } catch (exception: Exception) {
            emitError(exception)
        }
    }

    private suspend fun updateProgress(collector: FlowCollector<Result<Uri>>) {
        Timber.d("updateProgress")
        var downloading = true
        while (downloading) {

            val q = DownloadManager.Query()
            q.setFilterById(referenceId)
            val cursor = downloadManager!!.query(q)
            cursor.moveToFirst()

            val bytesDownloaded =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val bytesTotal =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

            if (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                downloading = false
            }
            val dlProgress = ((bytesDownloaded * 100L) / bytesTotal).toInt()
            collector.emit(Result.Progress(dlProgress))
            delay(100)
            cursor.close()
        }
    }
}