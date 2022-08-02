package com.tevibox.tvplay.presentation.screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.ActivityMainBinding
import com.tevibox.tvplay.presentation.viewmodel.KeyEventSharedViewModel
import com.tevibox.tvplay.presentation.viewmodel.MainActivityViewModel


class MainActivity
    : BaseBindingActivity<ActivityMainBinding, MainActivityViewModel>(R.layout.activity_main) {

    override fun getViewModelClass() = MainActivityViewModel::class

    private val keyEventSharedViewModel by lazy {
        ViewModelProvider(this)[KeyEventSharedViewModel::class.java]
    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestMultiplePermissions()) {
            val grantedPermissionsCount = it.filter { permission -> permission.value }.size
            if (grantedPermissionsCount == 2) {
                viewModel.navigateToMainScreen()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {
                        AlertDialog.Builder(this)
                            .setTitle(R.string.main_permissions_not_granted)
                            .setMessage(R.string.main_permission_explain)
                            .setOnCancelListener { finish() }
                            .setPositiveButton(R.string.main_permission_in_settings) { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivity(intent)
                                finish()
                            }.show()
                        return@registerForActivityResult
                    }
                }
                requestPermission()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.navigateToMainScreen()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        keyEventSharedViewModel.keyEventLiveData.value = event
        return (keyEventSharedViewModel.returnKeyEventLiveData.value ?: false)
                || super.dispatchKeyEvent(event)
    }
}