package com.tevibox.tvplay.presentation.screen.menu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.databinding.FragmentCategoryListBinding
import com.tevibox.tvplay.presentation.adapter.CategoryAdapter
import com.tevibox.tvplay.presentation.screen.BaseBindingFragment
import com.tevibox.tvplay.presentation.viewmodel.menu.CategoryListViewModel
import com.tevibox.tvplay.presentation.viewmodel.shared.TvViewModel

class CategoryListFragment :
    BaseBindingFragment<FragmentCategoryListBinding, CategoryListViewModel>(R.layout.fragment_category_list) {

    override fun getViewModelClass() = CategoryListViewModel::class

    private val tvViewModel: TvViewModel by activityViewModels()

    override fun onViewBindingCreated(binding: FragmentCategoryListBinding) {
        binding.categoryAdapter = CategoryAdapter {
            tvViewModel.selectCategory(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategories()
    }

    override fun initializeLiveData() {
        viewModel.settingsLiveData.observe(viewLifecycleOwner) {
            binding.categoryProgress.isVisible = false
            binding.categoryAdapter?.setItems(it.categories)

            tvViewModel.getSelectedCategory().value?.let { item ->
                selectCategory(item)
            }
        }

        tvViewModel.getSelectedCategory().observe(viewLifecycleOwner) {
            selectCategory(it)
        }
    }

    private fun selectCategory(category: Category) {
        binding.categoryAdapter?.selectItem(category)
    }
}