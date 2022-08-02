package com.tevibox.tvplay.presentation.adapter.epg

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.javavirys.core.presentation.adapter.BaseViewHolder
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Epg
import com.tevibox.tvplay.databinding.LayoutChannelEpgsItemBinding
import com.tevibox.tvplay.utils.extension.toDateString

class EpgViewHolder(
    parent: ViewGroup
) : BaseViewHolder<Epg, LayoutChannelEpgsItemBinding>(
    parent,
    R.layout.layout_channel_epgs_item
) {

    override fun bind(item: Epg) {
        itemView.setOnFocusChangeListener { _, hasFocus ->
            showFocusedItem(hasFocus)
        }
        binding?.let {
            it.timeTextView.text = itemView.context.getString(
                R.string.epg_start_to_end_time,
                (item.start * 1000).toDateString("HH:mm"),
                (item.stop * 1000).toDateString("HH:mm")
            )
            it.nameTextView.text = item.title
            it.statusTextView.isVisible = item.selected
        }
    }

    private fun showFocusedItem(hasFocus: Boolean) {
        val backgroundResource = if (hasFocus) {
            R.drawable.shape_round_rect_yellow
        } else {
            android.R.color.transparent
        }
        itemView.setBackgroundResource(backgroundResource)
    }
}