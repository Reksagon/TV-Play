package com.tevibox.tvplay.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.javavirys.core.extension.getColorCompat
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.databinding.LayoutChannelItemBinding

class StreamViewHolder(
    parent: ViewGroup,
    private val onItemLongClicked: (Stream, View) -> Unit
) : BaseSelectableViewHolder<Stream, LayoutChannelItemBinding>(
    parent,
    R.layout.layout_channel_item
) {

    override fun bind(binding: LayoutChannelItemBinding, item: Stream) {
        itemView.setOnLongClickListener {
            onItemLongClicked(item, it)
            true
        }

        binding.numberTextView.text = String.format("%03d", item.number)
        binding.nameTextView.text = item.name
        Glide.with(itemView.context)
            .load(item.logo)
            .placeholder(R.drawable.ic_baseline_live_tv_24)
            .into(binding.iconImageView)
        binding.favoriteImageView.isVisible = item.favorite
        binding.lockImageView.isVisible = item.lock
    }

    override fun showSelectedItem(item: Stream) {
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

    override fun showFocusedItem(item: Stream, hasFocus: Boolean) {
        val backgroundResource = if (hasFocus) {
            R.drawable.shape_round_rect_yellow
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }
}