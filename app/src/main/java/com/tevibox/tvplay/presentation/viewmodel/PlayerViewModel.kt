package com.tevibox.tvplay.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Router
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.core.entity.*
import com.tevibox.tvplay.core.exception.ResponseException
import com.tevibox.tvplay.core.exception.ResponseExceptionCodesEnum
import com.tevibox.tvplay.domain.interactor.*
import com.tevibox.tvplay.domain.interactor.channel.GetNextStreamInteractor
import com.tevibox.tvplay.domain.interactor.channel.GetPrevStreamInteractor
import com.tevibox.tvplay.domain.repository.ActivationCodeRepository
import com.tevibox.tvplay.domain.repository.StreamsRepository
import com.tevibox.tvplay.domain.repository.UserRepository
import com.tevibox.tvplay.presentation.navigation.Screens
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class PlayerViewModel(
    private val router: Router,
    private val getCurrentCategoryWithStreamInteractor: GetCurrentCategoryWithStreamInteractor,
    private val saveStreamAsCurrentInteractor: SaveStreamAsCurrentInteractor,
    private val streamsRepository: StreamsRepository,
    private val keepAliveInteractor: KeepAliveInteractor,
    private val getPrevStreamInteractor: GetPrevStreamInteractor,
    private val getNextStreamInteractor: GetNextStreamInteractor,
    private val getCurrentProgramListInteractor: GetCurrentProgramListInteractor,
    private val activationCodeRepository: ActivationCodeRepository,
    private val logoutInteractor: LogoutInteractor,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val startStreamLiveData = MutableLiveData<Stream>()

    val categoryWithStreamLiveData = MutableLiveData<CategoryWithStream>()

    val timerLiveData = MutableLiveData<Unit>()

    val currentEpgLiveData = MutableLiveData<CurrentEpg>()

    val activateSubscriptionLiveData = MutableLiveData<Result<Unit>>()

    val trialLiveData = MutableLiveData<Boolean>()

    var timerJob: Job? = null

    fun loadInformation() = launch {
        Timber.d("load()")
        loadCurrentUser()
        loadCurrentCategoryWithStreamInIO {
            setCurrentStream(it)
        }
        keepAlive()
    }

    private fun loadCurrentUser() = launch {
        userRepository.getCurrentUser()
            .collect {
                trialLiveData.postValue(it.subscriptionStatus == UserStatusEnum.TRIAL.status)
            }
    }

    private suspend fun loadCurrentCategoryWithStreamInIO(collectBlock: (CategoryWithStream) -> Unit) {
        getCurrentCategoryWithStreamInteractor(Unit).collect { collectBlock(it) }
    }

    fun setStream(category: Category, stream: Stream) = launch {
        val categoryWithStream = CategoryWithStream(category, stream)
        saveStreamAsCurrentInteractor(categoryWithStream)
        categoryWithStreamLiveData.postValue(categoryWithStream)
        setActiveStream(stream)
    }

    fun setActiveStream(stream: Stream) = launch {
        streamsRepository.setActiveStream(stream)
    }

    private fun keepAlive() = launch {
        while (true) {
            delay(KEEP_ALIVE_DELAY)
            keepAliveInteractor(Unit)
        }
    }

    fun gotoNextChannel() = launch {
        categoryWithStreamLiveData.value?.let { current ->
            getNextStreamInteractor(current).collect {
                setCurrentStream(it)
            }
        }
    }

    fun gotoPrevChannel() = launch {
        categoryWithStreamLiveData.value?.let { current ->
            getPrevStreamInteractor(current).collect {
                setCurrentStream(it)
            }
        }
    }

    private fun setCurrentStream(categoryWithStream: CategoryWithStream) {
        startStreamLiveData.postValue(categoryWithStream.stream)
        categoryWithStreamLiveData.postValue(categoryWithStream)
    }

    fun startTimer(delay: Long = TIMER_DELAY) {
        if (timerJob == null) {
            timerJob = launchWithReturnJob {
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < delay) {
                    delay(1000)
                }
                timerLiveData.postValue(Unit)
            }
        }
    }

    fun killTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun restartTimer(delay: Long = TIMER_DELAY) {
        if (timerJob != null) {
            killTimer()
        }
        startTimer(delay)
    }

    fun loadCurrentEpgs(stream: Stream) = launch {
        getCurrentProgramListInteractor(stream).collect {
            currentEpgLiveData.postValue(it)
        }
    }

    fun activateSubscription(code: String) = launch {
        activateSubscriptionLiveData.postValue(Result.Progress())
        activationCodeRepository.set(ActivationCode(code))
        activateSubscriptionLiveData.postValue(Result.Success(Unit))
        loadInformation()
    }

    override fun onException(throwable: Throwable) {
        if (throwable is ResponseException
            && (throwable.code == ResponseExceptionCodesEnum.NO_DEFINED_CARD_NUMBER.code
                    || throwable.code == ResponseExceptionCodesEnum.INVALID_CARD.code)
        ) {
            launch { activateSubscriptionLiveData.postValue(Result.Error(throwable)) }
        } else {
            super.onException(throwable)
        }
    }

    fun logout() = launch {
        logoutInteractor(Unit)
        router.newRootChain(Screens.getLoginScreen())
    }

    companion object {

        private const val KEEP_ALIVE_DELAY = 600_000L // 10min

        private const val TIMER_DELAY = 7_200_000L // 2 hour
    }
}