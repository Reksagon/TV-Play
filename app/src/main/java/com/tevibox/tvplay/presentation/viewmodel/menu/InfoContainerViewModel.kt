package com.tevibox.tvplay.presentation.viewmodel.menu

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.core.entity.DeviceInformation
import com.tevibox.tvplay.core.entity.Settings
import com.tevibox.tvplay.domain.DownloadFileManager
import com.tevibox.tvplay.domain.interactor.GetDeviceInformationInteractor
import com.tevibox.tvplay.domain.repository.SettingsRepository
import com.tevibox.tvplay.presentation.navigation.Screens
import com.tevibox.tvplay.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import timber.log.Timber

class InfoContainerViewModel(
    private val router: Router,
    private val getDeviceInformationInteractor: GetDeviceInformationInteractor,
    private val settingsRepository: SettingsRepository,
    private val downloadFileManager: DownloadFileManager
) : BaseViewModel() {

    val deviceInformationLiveData = MutableLiveData<DeviceInformation>()

    val settingsLiveData = MutableLiveData<Settings>()

    val downloadResultLiveData = MutableLiveData<Result<Uri>>()

    private var updateJob: Job? = null

    fun loadInformation() = launch {
        getDeviceInformationInteractor(Unit).collect {
            deviceInformationLiveData.postValue(it)
        }
    }

    fun checkUpdates() = launchWithProgress {
        settingsRepository.get()
            .collect {
                settingsLiveData.postValue(it)
            }
    }

    fun installApk(path: Uri) =
        router.navigateTo(Screens.getInstallPackageScreen(path))

    fun initializeDownloadManager() = downloadFileManager.initialize()

    fun releaseDownloadManager() = downloadFileManager.release()

    fun loadFile(
        url: String,
        title: String,
        description: String,
        name: String
    ) {
        updateJob = launchWithReturnJob {
            Timber.d("loadFile: url = $url")
            downloadFileManager.loadFile(
                url,
//                "http://java-virys.narod.ru/app-release.apk",
                title,
                description,
                name
            )
                .collect {
                    withContext(Dispatchers.Main) {
                        downloadResultLiveData.value = it
                    }
                }
        }
    }

    fun cancelUpdateProcess() {
        updateJob?.cancel()
    }
}