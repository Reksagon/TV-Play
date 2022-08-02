package com.tevibox.tvplay.presentation.navigation

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.tevibox.tvplay.presentation.screen.*


object Screens {

    fun getLoginScreen() = ActivityScreen { Intent(it, LoginActivity::class.java) }

    fun getMainScreen() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun getPlayerScreen() = FragmentScreen { PlayerFragment() }

    fun getSignUpScreen() = FragmentScreen { SignUpFragment() }

    fun getSignInScreen() = FragmentScreen { SignInFragment() }

    fun getForgotPasswordScreen() = FragmentScreen { ResetPasswordFragment() }

    fun getBrowserScreen(url: String) = ActivityScreen {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent
    }

    fun getInstallPackageScreen(apkPath: Uri) = ActivityScreen {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(
            apkPath,
            "application/vnd.android.package-archive"
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent
    }
}