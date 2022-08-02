package com.tevibox.tvplay.presentation.adapter.epg

import android.view.ViewGroup
import com.javavirys.core.presentation.adapter.BaseAdapter
import com.tevibox.tvplay.core.entity.Epg
import com.tevibox.tvplay.databinding.LayoutChannelEpgsItemBinding

class EpgAdapter(
    private val onItemClick: (Epg) -> Unit = {},
) : BaseAdapter<Epg, LayoutChannelEpgsItemBinding, EpgViewHolder>() {

    private var currentEpg: Epg? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EpgViewHolder(parent)

    fun setPlayingEpg(epg: Epg?) {
        if (epg == null) {
            currentEpg?.selected = false
            currentEpg = null
            return
        }
        items.forEachIndexed { index, item ->
            if (epg.title == item.title && epg.start == item.start && epg.stop == item.stop) {
                currentEpg?.selected = false
                epg.selected = true
                currentEpg = epg
                notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }
}