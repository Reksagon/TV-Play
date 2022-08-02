package com.tevibox.tvplay.presentation.screen.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.databinding.FragmentFavoriteContainerBinding
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.FavoriteViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel

class LockContainerFragment :
    BaseBindingFragment<FragmentFavoriteContainerBinding, FavoriteViewModel>(R.layout.fragment_favorite_container) {

    override fun getViewModelClass() = FavoriteViewModel::class

    private val tvViewModel: TvViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewModel.selectCategory(Category(0, getString(R.string.player_menu_item_lock)))
    }

    override fun initializeLiveData() {
    }
}