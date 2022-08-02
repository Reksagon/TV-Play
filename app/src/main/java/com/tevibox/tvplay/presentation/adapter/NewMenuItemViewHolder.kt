package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.databinding.LayoutMenuItemBinding

class NewMenuItemViewHolder(
    parent: ViewGroup
) : BaseSelectableViewHolder<MenuItem, LayoutMenuItemBinding>(
    parent,
    R.layout.layout_menu_item
) {

    override fun bind(binding: LayoutMenuItemBinding, item: MenuItem) {
        Glide.with(itemView)
            .load(item.icon)
            .into(binding.iconImageView)
        binding.nameTextView.setText(item.titleId)
    }

    override fun showSelectedItem(item: MenuItem) {
        val backgroundResource = if (item.selected) {
            R.drawable.shape_fill_round_rect_black
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }

    override fun showFocusedItem(item: MenuItem, hasFocus: Boolean) {
        if (item.selected) return
        val backgroundResource = if (hasFocus) {
            R.drawable.shape_round_rect_black
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }
}