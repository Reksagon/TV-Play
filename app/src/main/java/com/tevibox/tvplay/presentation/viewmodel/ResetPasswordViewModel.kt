package com.tevibox.tvplay.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.domain.interactor.ResetPasswordInteractor
import timber.log.Timber

class ResetPasswordViewModel(
    private val router: Router,
    private val resetPasswordInteractor: ResetPasswordInteractor
) : BaseViewModel() {

    val resetPasswordFlagLiveData = MutableLiveData<Result<Unit>>()

    fun resetPassword(email: String) = launch {
        Timber.d("forgotPassword email = $email")
        resetPasswordInteractor(email)
        resetPasswordFlagLiveData.postValue(Result.Success(Unit))
    }

    fun navigateToBack() = router.exit()
}