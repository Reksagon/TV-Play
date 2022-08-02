package com.tevibox.tvplay.presentation.screen.menu.account

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
import com.tevibox.tvplay.databinding.FragmentMyAccountContainerBinding
import com.tevibox.tvplay.presentation.dialog.LinearProgressDialog
import com.tevibox.tvplay.presentation.dialog.RenewSubscriptionDialog
import com.tevibox.tvplay.presentation.dialog.ToastDialog
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.InfoContainerViewModel
import com.tevibox.tvplay.presentation.viewmodel.menu.MyAccountContainerViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import com.tevibox.tvplay.utils.extension.toDateString
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.net.SocketTimeoutException

class MyAccountContainerFragment :
    BaseBindingFragment<FragmentMyAccountContainerBinding, MyAccountContainerViewModel>(R.layout.fragment_my_account_container) {

    override fun getViewModelClass() = MyAccountContainerViewModel::class

    private val infoViewModel: InfoContainerViewModel by viewModel()

    private var progressDialog: LinearProgressDialog? = null

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentMyAccountContainerBinding) {
        binding.renewButton.setOnClickListener {
            RenewSubscriptionDialog.newInstance(viewModel::activateSubscription)
                .show(parentFragmentManager)
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        binding.updateButton.setOnClickListener {
            infoViewModel.checkUpdates()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadInformation()
        infoViewModel.initializeDownloadManager()
        infoViewModel.loadInformation()
    }

    override fun initializeLiveData() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.accountTextView.text = it.email.ifEmpty { getString(R.string.none) }
            binding.statusTextView.text = it.subscriptionStatus
            binding.expireDateTextView.text = if (it.subscriptionExpire == -1L) {
                getString(R.string.none)
            } else {
                (it.subscriptionExpire * 1000).toDateString()
            }
        }

        infoViewModel.deviceInformationLiveData.observe(viewLifecycleOwner) {
            binding.appVersionTextView.text = BuildConfig.VERSION_NAME
            binding.firmwareTextView.text = it.firmwareVersion
        }

        infoViewModel.settingsLiveData.observe(viewLifecycleOwner) {
            if (it.appLatestVersion != BuildConfig.VERSION_CODE) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.info_new_version_available)
                    .setPositiveButton(R.string.info_download) { _, _ ->
                        controlsViewModel.setPlayerState(PlayerStateEnum.RELEASE)
                        progressDialog = LinearProgressDialog().also { dialog ->
                            dialog.setTitle(getString(R.string.info_download_preparing))
                            dialog.setOnCancelListener {
                                controlsViewModel.setPlayerState(PlayerStateEnum.INITIALIZE)
                                infoViewModel.cancelUpdateProcess()
                            }
                            dialog.show(parentFragmentManager)
                        }
                        infoViewModel.loadFile(
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

        infoViewModel.downloadResultLiveData.observe(viewLifecycleOwner) {
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
                    infoViewModel.installApk(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        infoViewModel.releaseDownloadManager()
        super.onDestroyView()
    }

    override fun showProgress() {
        binding.content.isInvisible = true
        binding.progressBar.isVisible = true

        binding.updateButton.isInvisible = true
        binding.updateProgress.isVisible = true
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
        binding.content.isInvisible = false

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