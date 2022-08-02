package com.tevibox.tvplay.presentation.screen.menu.channel

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.FragmentEpgListBinding
import com.tevibox.tvplay.presentation.adapter.epg.EpgAdapter
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.EpgListViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel
import com.tevibox.tvplay.utils.extension.toDateString

class EpgListFragment :
    BaseBindingFragment<FragmentEpgListBinding, EpgListViewModel>(R.layout.fragment_epg_list) {

    override fun getViewModelClass() = EpgListViewModel::class

    private val tvViewModel: TvViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentEpgListBinding) {
        binding.adapter = EpgAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initializeLiveData() {
        tvViewModel.getPlayingStreamLiveData().observe(viewLifecycleOwner) {
            binding.channelTextView.text = it.name
            binding.adapter?.setItems(it.epgList)
            val currentEpg = it.epgList.getOrNull(0)
            binding.adapter?.setPlayingEpg(currentEpg)
            binding.currentProgramConstraintLayout.isVisible = false
            currentEpg?.let { epg ->
                binding.currentProgramConstraintLayout.isVisible = true
                binding.epgRecyclerView.smoothScrollToPosition(0)
                binding.timeTextView.text = getString(
                    R.string.epg_start_to_end_time,
                    (epg.start * 1000).toDateString("HH:mm"),
                    (epg.stop * 1000).toDateString("HH:mm")
                )
                binding.programTextView.text = epg.title
                binding.descriptionTextView.text = epg.description
            }
        }
    }
}