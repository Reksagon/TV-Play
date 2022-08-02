package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.databinding.LayoutChannelCategoriesItemBinding

class CategoryAdapter(
    private val onItemClick: (Category) -> Unit,
) : BaseSelectableAdapter<Category, LayoutChannelCategoriesItemBinding, CategoryViewHolder>() {

    override fun onCreateSelectableViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CategoryViewHolder(parent)

    override fun onItemClicked(item: Category) {
        super.onItemClicked(item)
        onItemClick(item)
    }

    fun selectItem(category: Category) {
        if (selectedItem?.name == category.name) return
        items.forEachIndexed { index, item ->
            if (category.name == item.name) {
//                item.requestFocus = true
                super.onItemClicked(item)
                notifyItemChanged(index)
                return
            }
        }
    }
}