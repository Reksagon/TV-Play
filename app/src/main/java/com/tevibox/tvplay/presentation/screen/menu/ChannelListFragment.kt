package com.tevibox.tvplay.presentation.screen.menu

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.databinding.FragmentChannelListBinding
import com.tevibox.tvplay.presentation.adapter.StreamAdapter
import com.tevibox.tvplay.presentation.adapter.diffutil.StreamDiffUtilCallback
import com.tevibox.tvplay.presentation.dialog.LockChannelDialog
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.ChannelListViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel
import com.tevibox.tvplay.presentation.widget.MyPopupMenu
import com.tevibox.tvplay.utils.recyclerview.NotifyDataSetChangedListener
import timber.log.Timber

class ChannelListFragment :
    BaseBindingFragment<FragmentChannelListBinding, ChannelListViewModel>(R.layout.fragment_channel_list) {

    override fun getViewModelClass() = ChannelListViewModel::class

    private val tvViewModel: TvViewModel by activityViewModels()

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentChannelListBinding) {
        binding.adapter = StreamAdapter(::onItemClicked, ::onItemLongClicked)
        binding.searchEditText.doAfterTextChanged {
            Timber.d("Search query: ${it.toString()}")
            if (it.isNullOrEmpty()) {
                viewModel.streamsLiveData.value?.let { streams ->
                    updateList(streams.streams)
                }
                return@doAfterTextChanged
            }

            viewModel.streamsLiveData.value?.streams?.filter { stream ->
                stream.name.startsWith(it.toString(), true)
            }?.let { streams -> updateList(streams) }
        }
    }

    private fun updateList(newList: List<Stream>) {
        binding.adapter?.items?.let {
            val streamDiffUtilCallback = StreamDiffUtilCallback(it, newList)
            val serverDiffResult = DiffUtil.calculateDiff(streamDiffUtilCallback)

            binding.adapter?.let { adapter ->
                adapter.setItemsWithoutNotifyDataSetChanged(newList)
                serverDiffResult.dispatchUpdatesTo(adapter)
            }
        }
    }

    private fun onItemClicked(item: Stream) {
        tvViewModel.getPlayingStreamLiveData().value?.let {
            if (item.url == it.url && item.name == it.name) {
                controlsViewModel.setHideMenuFlag()
                return
            }
        }
        if (item.lock) {
            LockChannelDialog { password ->
                if (password == item.password) {
                    tvViewModel.selectPlayingStream(item)
                }
            }.show(childFragmentManager)
        } else {
            tvViewModel.getSelectedCategory().value?.let {
                tvViewModel.selectCurrentCategory(it)
            }

            tvViewModel.selectPlayingStream(item)
        }
    }

    private fun onItemLongClicked(item: Stream, view: View) {
        showContextMenu(item, view)
    }

    private fun showContextMenu(stream: Stream, view: View) {
        Timber.d("showContextMenu().stream = $stream")
        controlsViewModel.setShowChannelContextMenuFlag(true)
        val popup = MyPopupMenu(requireContext(), view)
        val favoriteStrId =
            if (stream.favorite) R.string.channels_remove_from_favorites else R.string.channels_add_to_favorites
        val lockStrId = if (stream.lock) R.string.channels_unlock else R.string.channels_lock
        popup.menu.add(favoriteStrId)
        popup.menu.add(lockStrId)

        popup.setOnCancelListener {
            controlsViewModel.setShowChannelContextMenuFlag(false)
        }
        popup.setOnMenuItemClickListener {
            when (it.title) {
                getString(R.string.channels_add_to_favorites), getString(R.string.channels_remove_from_favorites) -> {
                    addOrRemoveFavoriteChannel(stream)
                    controlsViewModel.setShowChannelContextMenuFlag(false)
                }
                getString(R.string.channels_lock), getString(R.string.channels_unlock) -> {
                    LockChannelDialog { password ->
                        onLockPasswordEntered(password, stream)
                        controlsViewModel.setShowChannelContextMenuFlag(false)
                    }.setOnCancelListener {
                        controlsViewModel.setShowChannelContextMenuFlag(false)
                    }.show(childFragmentManager)
                }
                else -> controlsViewModel.setShowChannelContextMenuFlag(false)
            }

            true
        }
        popup.show()
    }

    private fun addOrRemoveFavoriteChannel(item: Stream) {
        item.favorite = !item.favorite
        tvViewModel.getSelectedCategory().value?.let {
            if (it.name == getString(R.string.player_menu_item_favorite)) {
                binding.adapter?.removeAndNotify(item)
            } else {
                binding.adapter?.updateItem(item)
            }
        }

        viewModel.addOrRemoveFavoriteChannel(item)
    }

    private fun onLockPasswordEntered(password: String, item: Stream) {
        if (item.lock && password == item.password) {
            lockOrUnlock(item)
        } else if (!item.lock) {
            item.password = password
            lockOrUnlock(item)
        }
    }

    private fun lockOrUnlock(item: Stream) {
        item.lock = !item.lock
        tvViewModel.getSelectedCategory().value?.let {
            if (it.name == getString(R.string.player_menu_item_lock)) {
                binding.adapter?.removeAndNotify(item)
            } else {
                binding.adapter?.updateItem(item)
            }
        }
        viewModel.lockOrUnlockChannel(item)
    }

    override fun initializeLiveData() {
        tvViewModel.getSelectedCategory().observe(viewLifecycleOwner) {
            loadStreams(it)
        }

        viewModel.streamsLiveData.observe(viewLifecycleOwner) {
            binding.channelProgress.isVisible = false
            binding.channelRecyclerView.isInvisible = false
            binding.searchEditText.isVisible = it.streams.isNotEmpty()
            binding.adapter?.registerAdapterDataObserver(NotifyDataSetChangedListener(::selectItem))
            tvViewModel.getSelectedCategory().value?.let { category ->
                if ((category.name == getString(R.string.player_menu_item_favorite)
                            || category.name == getString(R.string.player_menu_item_lock))
                    && it.streams.isEmpty()
                ) {
                    binding.hintTextView.isVisible = true
                    return@observe
                }
            }
            binding.adapter?.setItems(it.streams)
        }
    }

    private fun selectItem() {
        tvViewModel.getPlayingStreamLiveData().value?.let {
            if (tvViewModel.getFocusOnSelectedChannelFlag().value == true) {
                binding.adapter?.selectItem(it)?.let { index ->
                    binding.channelRecyclerView.smoothScrollToPosition(index)
                    controlsViewModel.setShowLeftMenuLayoutFlag(false)
                }
            } else {
                binding.adapter?.selectItem(it, false)
            }
        }
    }

    private fun loadStreams(category: Category?) {
        Timber.d("initializeLiveData().category = $category")
        category?.let {
            binding.channelProgress.isVisible = true
            binding.channelRecyclerView.isInvisible = true
            binding.searchEditText.isVisible = false
            binding.searchEditText.text.clear()
            when (category.name) {
                getString(R.string.player_menu_item_favorite) -> {
                    viewModel.loadFilters()
                }
                getString(R.string.player_menu_item_lock) -> {
                    viewModel.loadLockChannels()
                }
                else -> viewModel.loadChannels(category.name)
            }
        }
    }

    override fun showException(throwable: Throwable) {
        binding.channelProgress.isVisible = false
        super.showException(throwable)
    }
}