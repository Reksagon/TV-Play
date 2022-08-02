package com.tevibox.tvplay.presentation.screen.menu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.MenuEnum
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.databinding.FragmentMenuBinding
import com.tevibox.tvplay.presentation.adapter.NewMenuItemAdapter
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.screen.menu.account.MyAccountContainerFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.MenuViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.ControlsViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel
import com.tevibox.tvplay.utils.FragmentUtils

class MenuFragment :
    BaseBindingFragment<FragmentMenuBinding, MenuViewModel>(R.layout.fragment_menu) {

    override fun getViewModelClass() = MenuViewModel::class

    private val controlsViewModel: ControlsViewModel by activityViewModels()

    private val tvViewModel: TvViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentMenuBinding) {
        val adapter = NewMenuItemAdapter(::onMenuItemClicked)
        binding.adapter = adapter
        binding.menuContainerConstraintLayout.onFocusSearchListener = ::onFocusSearchListener
    }

    private fun onFocusSearchListener(
        focused: View?,
        newViewFocused: View?,
        direction: Int
    ): View? {
        when (focused?.id) {
            R.id.categoryContainer -> {
                if (direction == View.FOCUS_LEFT) {
                    controlsViewModel.setShowLeftMenuLayoutFlag(true)
                    controlsViewModel.showEpgMenu(false)
                } else if (direction == View.FOCUS_RIGHT) {
                    controlsViewModel.setShowLeftMenuLayoutFlag(false)
                    controlsViewModel.showCategoriesMenu(false)
                    if (newViewFocused?.id != R.id.channelItemContainer) {
                        return binding.menuContainerConstraintLayout
                            .findViewById(R.id.channelItemContainer)
                    }
                }
            }
            R.id.channelItemContainer -> {
                if (direction == View.FOCUS_RIGHT) {
                    controlsViewModel.showEpgMenu(true)
                } else if (direction == View.FOCUS_LEFT) {
                    controlsViewModel.showEpgMenu(false)
                    controlsViewModel.showCategoriesMenu(true)
                }
            }
            R.id.epgContainer -> {
                if (direction == View.FOCUS_LEFT) {
                    if (newViewFocused?.id != R.id.channelItemContainer) {
                        return binding.menuContainerConstraintLayout
                            .findViewById(R.id.channelItemContainer)
                    }
                }
            }
        }

        return newViewFocused
    }

    override fun initializeLiveData() {
        viewModel.menuItemsLiveData.observe(viewLifecycleOwner) {
            binding.adapter?.setItems(it)

            controlsViewModel.getSelectedItemLeftMenuLiveData()
                .value
                ?.let(::selectMenuItem)
        }
        controlsViewModel.getShowLeftMenuLayoutFlag().observe(viewLifecycleOwner) {
            binding.menuRecyclerViewContainer.isVisible = it
            binding.leftMenuHintImageView.isVisible = !it
        }

        controlsViewModel.getSelectedItemLeftMenuLiveData()
            .observe(viewLifecycleOwner, ::selectMenuItem)
    }

    private fun selectMenuItem(index: Int) {
        binding.adapter?.selectItemById(index)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadInformation()
    }

    private fun onMenuItemClicked(item: MenuItem) = onMenuItemClicked(item.id)

    private fun onMenuItemClicked(itemId: Int) {
        tvViewModel.focusOnSelectedChannel(false)
        runFragmentTransactionWithRemoveCurrentFragment {
            when (itemId) {
                MenuEnum.MY_ACCOUNT.ordinal -> addMenuFragment<MyAccountContainerFragment>(it)
                MenuEnum.CHANNEL_LIST.ordinal -> addMenuFragment<TvProgramSelectorFragment>(it)
                MenuEnum.FAVORITE.ordinal -> addMenuFragment<FavoriteContainerFragment>(it)
                MenuEnum.LOCK.ordinal -> addMenuFragment<LockContainerFragment>(it)
//                MenuEnum.INFO.ordinal -> addMenuFragment<InfoContainerFragment>(it)
                else -> {}
            }
        }
    }

    private fun runFragmentTransactionWithRemoveCurrentFragment(block: (FragmentTransaction) -> Unit) =
        FragmentUtils.runFragmentTransactionWithRemoveCurrentFragment(
            childFragmentManager,
            FRAGMENT_MENU_TAG,
            block
        )

    private inline fun <reified F : Fragment> addMenuFragment(transaction: FragmentTransaction) =
        FragmentUtils.addFragment<F>(
            transaction,
            binding.fragmentContainer.id,
            FRAGMENT_MENU_TAG
        )

    companion object {
        private const val FRAGMENT_MENU_TAG = "FRAGMENT_MENU_TAG"
    }
}