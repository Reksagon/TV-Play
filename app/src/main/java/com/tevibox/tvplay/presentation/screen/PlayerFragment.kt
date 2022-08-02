package com.tevibox.tvplay.presentation.screen

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.javavirys.core.entity.Result
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.MenuEnum
import com.tevibox.tvplay.core.entity.PlayerStateEnum
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.core.exception.ResponseException
import com.tevibox.tvplay.core.exception.ResponseExceptionCodesEnum
import com.tevibox.tvplay.databinding.FragmentPlayerBinding
import com.tevibox.tvplay.presentation.dialog.ActivateSubscriptionDialog
import com.tevibox.tvplay.presentation.dialog.LockChannelDialog
import com.tevibox.tvplay.presentation.viewmodel.KeyEventSharedViewModel
import com.tevibox.tvplay.presentation.viewmodel.PlayerViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel
import com.tevibox.tvplay.utils.extension.volumeDown
import com.tevibox.tvplay.utils.extension.volumeUp
import com.tevibox.tvplay.utils.player.DemoUtil
import timber.log.Timber

class PlayerFragment
    : BaseBindingFragment<FragmentPlayerBinding, PlayerViewModel>(R.layout.fragment_player),
    Player.Listener, StyledPlayerControlView.VisibilityListener {

    private var dataSourceFactory: DataSource.Factory? = null

    override fun getViewModelClass() = PlayerViewModel::class

    private val keyEventSharedViewModel: KeyEventSharedViewModel by activityViewModels()

    private val tvViewModel: TvViewModel by activityViewModels()

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    private var player: ExoPlayer? = null

    private var playerControlContainer: ConstraintLayout? = null

    private var channelLogoImageView: ImageView? = null

    private var channelNameTextView: TextView? = null

    private var currentProgramTextView: TextView? = null

    private var nextProgramTextView: TextView? = null

    private var programLinearProgressIndicator: LinearProgressIndicator? = null

    private var subscriptionDialog: ActivateSubscriptionDialog? = null

    override fun onViewBindingCreated(binding: FragmentPlayerBinding) {
        playerControlContainer = binding.playerView.findViewById(R.id.playerControlContainer)
        channelLogoImageView = binding.playerView.findViewById(R.id.channelLogoImageView)
        channelNameTextView = binding.playerView.findViewById(R.id.channelNameTextView)
        currentProgramTextView = binding.playerView.findViewById(R.id.currentProgramTextView)
        nextProgramTextView = binding.playerView.findViewById(R.id.nextProgramTextView)
        programLinearProgressIndicator = binding.playerView
            .findViewById(R.id.programLinearProgressIndicator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSourceFactory = DemoUtil.getDataSourceFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startTimer()
        viewModel.loadInformation()
    }

    override fun onDestroyView() {
        viewModel.killTimer()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
            binding.playerView.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
            binding.playerView.onResume()
        }
        if (player?.isPlaying == false)
            player?.play()
    }

    override fun onPause() {
        super.onPause()

        if (player?.isPlaying == true)
            player?.stop()

        if (Util.SDK_INT <= 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    override fun initializeLiveData() {
        keyEventSharedViewModel.keyEventLiveData.observe(viewLifecycleOwner) {
            viewModel.restartTimer()
            when (it.keyCode) {
                KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_DPAD_CENTER -> {
                    if (it.action == KeyEvent.ACTION_UP && !binding.playerMenu.isVisible) {
                        binding.playerView.hideController()
                        controlsViewModel.setShowLeftMenuLayoutFlag(false)
                        controlsViewModel.showCategoriesMenu(false)
                        controlsViewModel.setSelectedItemLeftMenuLiveData(MenuEnum.CHANNEL_LIST.ordinal)
                        tvViewModel.focusOnSelectedChannel(true)
                        binding.playerMenu.isVisible = true

                        viewModel.categoryWithStreamLiveData.value?.let { categoryWithStream ->
                            tvViewModel.selectCategory(categoryWithStream.category)
                        }
                    }
                }
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.playerMenu.isVisible && it.action == KeyEvent.ACTION_UP) {
                        controlsViewModel.showEpgMenu(false)
                        binding.playerMenu.isVisible = false
                        keyEventSharedViewModel.returnKeyEventLiveData.value = true
                        return@observe
                    } else if (it.action == KeyEvent.ACTION_UP) {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.player_want_exit)
                            .setPositiveButton(R.string.yes) { _, _ ->
                                finishAffinity(requireActivity())
                                System.exit(0)
                            }
                            .setNegativeButton(R.string.no) { _, _ -> }
                            .show()
                        keyEventSharedViewModel.returnKeyEventLiveData.value = true
                        return@observe
                    }
                }
                KeyEvent.KEYCODE_DPAD_UP -> {
                    if (!binding.playerMenu.isVisible && it.action != KeyEvent.ACTION_DOWN) {
                        viewModel.gotoPrevChannel()
                    }
                }
                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (!binding.playerMenu.isVisible && it.action != KeyEvent.ACTION_DOWN) {
                        viewModel.gotoNextChannel()
                    }
                }
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    if (!binding.playerMenu.isVisible && it.action == KeyEvent.ACTION_UP) {
                        requireContext().volumeDown()
                    }
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (!binding.playerMenu.isVisible && it.action == KeyEvent.ACTION_UP) {
                        requireContext().volumeUp()
                    }
                }
            }

            keyEventSharedViewModel.returnKeyEventLiveData.value =
                if (binding.playerMenu.isVisible) false
                else binding.playerView.dispatchKeyEvent(it)
        }

        tvViewModel.getPlayingStreamLiveData().observe(viewLifecycleOwner) {
            binding.playerView.onPause()
            releasePlayer()
            initializePlayer()
            player?.play()
            updatePlayerControlLayout(it)
        }

        viewModel.categoryWithStreamLiveData.observe(viewLifecycleOwner) {
            tvViewModel.selectCurrentCategory(it.category)
        }

        viewModel.startStreamLiveData.observe(viewLifecycleOwner) {
            if (it.lock) {
                LockChannelDialog { password ->
                    if (password == it.password) {
                        tvViewModel.selectPlayingStream(it)
                        viewModel.setActiveStream(it)
                    }
                }.show(childFragmentManager)
                return@observe
            }
            tvViewModel.selectPlayingStream(it)
            viewModel.setActiveStream(it)
        }

        controlsViewModel.getHideMenuFlag().observe(viewLifecycleOwner) {
            binding.playerMenu.isVisible = false
        }

        viewModel.timerLiveData.observe(viewLifecycleOwner) {
            Timber.d("The timer has been triggered!")
            player?.pause()
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.player_continue_watching)
                .setPositiveButton(R.string.yes) { _, _ ->
                    releasePlayer()
                    initializePlayer()
                    player?.play()
                    viewModel.restartTimer()
                }.show()
        }

        viewModel.currentEpgLiveData.observe(viewLifecycleOwner) {
            Timber.d("progressProgramLiveData: $it")

            val current = it.currentEpg
            val next = it.nextEpg

            programLinearProgressIndicator?.progress = it.progressEpg

            currentProgramTextView?.text = if (current?.title.isNullOrEmpty()) {
                ""
            } else {
                getString(R.string.player_current_played, current?.title)
            }

            nextProgramTextView?.text = if (next?.title.isNullOrEmpty()) {
                ""
            } else {
                getString(R.string.player_next_played, next?.title)
            }
        }
        viewModel.activateSubscriptionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    subscriptionDialog?.hideProgress()
                    subscriptionDialog?.dismiss()
                    subscriptionDialog = null
                }
                is Result.Progress -> {
                    subscriptionDialog?.hideErrorMessage()
                    subscriptionDialog?.showProgress()
                }
                is Result.Error -> {
                    subscriptionDialog?.hideProgress()
                    subscriptionDialog?.showErrorMessage(
                        getString(
                            R.string.dialog_active_subscription_error,
                            it.throwable.toString()
                        )
                    )
                }
            }
        }

        viewModel.trialLiveData.observe(viewLifecycleOwner) {
            binding.trialTextView.isVisible = it
        }

        controlsViewModel.getPlayerControlState()
            .observe(viewLifecycleOwner) {
                when (it) {
                    PlayerStateEnum.INITIALIZE -> {
                        initializePlayer()
                        player?.play()
                    }
                    PlayerStateEnum.RELEASE -> {
                        player?.stop()
                        releasePlayer()
                    }
                    else -> {}
                }
            }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        Timber.d("onPlaybackStateChanged().state = $playbackState")
        when (playbackState) {
            Player.STATE_BUFFERING -> binding.progressBar.isVisible = true
            else -> binding.progressBar.isVisible = false
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) = Unit

    override fun onVisibilityChange(visibility: Int) {
        Timber.d("onVisibilityChange.visibility = $visibility")
    }

    private fun updatePlayerControlLayout(stream: Stream) {
        playerControlContainer?.isVisible = !binding.playerMenu.isVisible

        channelLogoImageView?.let {
            Glide.with(requireContext())
                .load(stream.logo)
                .placeholder(R.drawable.ic_baseline_live_tv_24)
                .into(it)
        }
        channelNameTextView?.text = String.format("%03d %s", stream.number, stream.name)
        viewModel.loadCurrentEpgs(stream)
    }

    private fun initializePlayer() {
        val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory!!)

        player = ExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        player?.let {
            it.addListener(this)
            buildMediaItem()?.let(it::setMediaItem)
            binding.playerView.player = it
            it.prepare()
        }
        binding.playerView.setControllerVisibilityListener(this)
    }

    private fun buildMediaItem() = tvViewModel.getPlayingStreamLiveData()
        .value
        ?.let {
            tvViewModel.getCurrentCategory().value?.let { category ->
                viewModel.setStream(category, it)
            }
            MediaItem.fromUri(it.url)
        }

    private fun releasePlayer() {
        player?.let {
            it.release()
            player = null
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        Timber.d("onPlayerError: $error")
        if (error is ExoPlaybackException) {
            releasePlayer()
            initializePlayer()
            player?.play()
        } else {
            showException(error)
        }
    }

    override fun showException(throwable: Throwable) {
        if (throwable is ResponseException) {
            if (throwable.code == ResponseExceptionCodesEnum.NO_ACTIVE_SUBSCRIPTION.code) {
                subscriptionDialog?.dismiss()
                subscriptionDialog = null
                subscriptionDialog = ActivateSubscriptionDialog(
                    getString(R.string.dialog_active_subscription_subscription_is_not_active),
                    getString(R.string.dialog_active_subscription_activate_your_subscription)
                ) { code ->
                    subscriptionDialog?.showProgress()
                    viewModel.activateSubscription(code)
                }.setOnLogoutClickListener {
                    viewModel.logout()
                }.also {
                    it.show(childFragmentManager)
                }
                return
            }
        }

        super.showException(throwable)
    }
}