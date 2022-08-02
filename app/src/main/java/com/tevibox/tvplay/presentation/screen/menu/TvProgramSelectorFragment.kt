package com.tevibox.tvplay.presentation.screen.menu

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.tevibox.tvplay.R
import com.tevibox.tvplay.databinding.FragmentTvProgramSelectorBinding
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.TvProgramSelectorViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel

class TvProgramSelectorFragment :
    BaseBindingFragment<FragmentTvProgramSelectorBinding, TvProgramSelectorViewModel>(R.layout.fragment_tv_program_selector) {

    override fun getViewModelClass() = TvProgramSelectorViewModel::class

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    private val tvViewModel: TvViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentTvProgramSelectorBinding) {
        tvViewModel.getSelectedCategory().value?.let {
            if (it.name == getString(R.string.player_menu_item_favorite)
                || it.name == getString(R.string.player_menu_item_lock)
            ) {
                tvViewModel.getCurrentCategory().value?.let { current ->
                    tvViewModel.selectCategory(current)
                }
            }
        }
    }

    override fun initializeLiveData() {
        controlsViewModel.getShowEpgMenuFlag().observe(viewLifecycleOwner) {
            binding.epgFragmentContainer.isVisible = it
            binding.epgHintImageView.isVisible = !it
        }

        controlsViewModel.getShowCategoriesMenuFlag().observe(viewLifecycleOwner) {
            binding.categoryFragmentContainer.isVisible = it
        }
    }
}