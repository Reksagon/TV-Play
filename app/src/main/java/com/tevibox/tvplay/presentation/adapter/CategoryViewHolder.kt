package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.javavirys.core.extension.getColorCompat
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.databinding.LayoutChannelCategoriesItemBinding

class CategoryViewHolder(
    parent: ViewGroup
) : BaseSelectableViewHolder<Category, LayoutChannelCategoriesItemBinding>(
    parent,
    R.layout.layout_channel_categories_item
) {

    override fun bind(binding: LayoutChannelCategoriesItemBinding, item: Category) {
        binding.nameTextView.text = item.name
    }

    override fun showSelectedItem(item: Category) {
        val color = if (item.selected) {
            itemView.context.getColorCompat(R.color.border_color)
        } else {
            itemView.context.getColorCompat(R.color.white)
        }
        binding?.nameTextView?.setTextColor(color)

        if (item.requestFocus) {
            item.requestFocus = false
            itemView.requestFocus()
        }
    }

    override fun showFocusedItem(item: Category, hasFocus: Boolean) {
        val backgroundResource = if (hasFocus) {
            R.drawable.shape_round_rect_yellow
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }
}