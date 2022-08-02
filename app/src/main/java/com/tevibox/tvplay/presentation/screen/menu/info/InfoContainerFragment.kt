package com.tevibox.tvplay.presentation.screen.menu.info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.PlayerStateEnum
import com.tevibox.tvplay.databinding.FragmentInfoContainerBinding
import com.tevibox.tvplay.presentation.dialog.LinearProgressDialog
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.InfoContainerViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import timber.log.Timber
import java.net.SocketTimeoutException

class InfoContainerFragment :
    BaseBindingFragment<FragmentInfoContainerBinding, InfoContainerViewModel>(R.layout.fragment_info_container) {

    override fun getViewModelClass() = InfoContainerViewModel::class

    private var progressDialog: LinearProgressDialog? = null

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentInfoContainerBinding) {
        binding.updateButton.setOnClickListener {
            viewModel.checkUpdates()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initializeDownloadManager()
        viewModel.loadInformation()
    }

    override fun initializeLiveData() {
        viewModel.deviceInformationLiveData.observe(viewLifecycleOwner) {
            binding.accountTextView.text = BuildConfig.VERSION_NAME
            binding.firmwareTextView.text = it.firmwareVersion
        }

        viewModel.settingsLiveData.observe(viewLifecycleOwner) {
            if (it.appLatestVersion != BuildConfig.VERSION_CODE) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.info_new_version_available)
                    .setPositiveButton(R.string.info_download) { _, _ ->
                        controlsViewModel.setPlayerState(PlayerStateEnum.RELEASE)
                        progressDialog = LinearProgressDialog().also { dialog ->
                            dialog.setTitle(getString(R.string.info_download_preparing))
                            dialog.setOnCancelListener {
                                controlsViewModel.setPlayerState(PlayerStateEnum.INITIALIZE)
                                viewModel.cancelUpdateProcess()
                            }
                            dialog.show(parentFragmentManager)
                        }
                        viewModel.loadFile(
                            it.appLatestVersionUrl,
                            getString(R.string.app_name),
                            "New Version",
                            "app.apk"
                        )
                    }.show()

            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.info_you_using_latest_version)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }

        viewModel.downloadResultLiveData.observe(viewLifecycleOwner) {
            Timber.d("downloadResultLiveData: $it")
            when (it) {
                is Result.Progress -> {
                    Timber.d("Progress: ${it.progress}")
                    progressDialog?.let { dialog ->
                        dialog.setTitle(getString(R.string.info_downloading_apk, it.progress))
                        dialog.setProgress(it.progress)
                    }
                }
                is Result.Error -> {
                    controlsViewModel.setPlayerState(PlayerStateEnum.INITIALIZE)
                    progressDialog?.dismiss()
                    showException(it.throwable)
                }
                is Result.Success -> {
                    controlsViewModel.setPlayerState(PlayerStateEnum.INITIALIZE)
                    progressDialog?.dismiss()
                    viewModel.installApk(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        viewModel.releaseDownloadManager()
        super.onDestroyView()
    }

    override fun showProgress() {
        binding.updateButton.isInvisible = true
        binding.updateProgress.isVisible = true
    }

    override fun hideProgress() {
        binding.updateButton.isInvisible = false
        binding.updateProgress.isVisible = false
    }

    override fun showException(throwable: Throwable) {
        progressDialog?.dismiss()
        if (throwable is SocketTimeoutException) {
            ToastDialog().setMessage(getString(R.string.no_server_connection))
                .show(parentFragmentManager, 3)
        } else {
            super.showException(throwable)
        }
    }
}