package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.javavirys.core.presentation.adapter.BaseViewHolder
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.databinding.LayoutMenuItemBinding

class MenuItemViewHolder(
    parent: ViewGroup,
    private val onItemFocused: (MenuItem) -> Unit
) : BaseViewHolder<MenuItem, LayoutMenuItemBinding>(parent, R.layout.layout_menu_item) {

    override fun bind(item: MenuItem) {
        binding?.let {
            itemView.isFocusableInTouchMode = true
            itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    item.selected = true
                    onItemFocused(item)
                }
            }

            itemView.setOnClickListener {
                itemView.requestFocus()
            }

            Glide.with(itemView)
                .load(item.icon)
                .into(it.iconImageView)
            it.nameTextView.setText(item.titleId)

            changeMenuItemContainerColor(item)
        }
    }

    private fun changeMenuItemContainerColor(item: MenuItem) {
        val backgroundResource = if (item.selected) {
            R.drawable.shape_round_rect_black
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }
}