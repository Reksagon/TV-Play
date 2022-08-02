package com.tevibox.tvplay.presentation.adapter

import android.view.View
import android.view.ViewGroup
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.databinding.LayoutChannelItemBinding
import timber.log.Timber

class StreamAdapter(
    private val onItemClick: (Stream) -> Unit,
    private val onItemLongClicked: (Stream, View) -> Unit
) : BaseSelectableAdapter<Stream, LayoutChannelItemBinding, StreamViewHolder>() {

    override fun onCreateSelectableViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = StreamViewHolder(parent, onItemLongClicked)

    override fun onItemClicked(item: Stream) {
        super.onItemClicked(item)
        onItemClick(item)
    }

    fun removeAndNotify(item: Stream) {
        val iterator = items.iterator()
        var index = 0
        while (iterator.hasNext()) {
            if (iterator.next().id == item.id) {
                iterator.remove()
                notifyItemRemoved(index)
                break
            }
            index++
        }
    }

    fun updateItem(item: Stream) {
        items.forEachIndexed { index, stream ->
            if (item.name == stream.name && item.url == stream.url) {
                Timber.d("updateItem: $item")
                notifyItemChanged(index)
                return
            }
        }
    }

    fun selectItem(stream: Stream, requestFocus: Boolean = true): Int {
        items.forEachIndexed { index, item ->
            if (stream.name == item.name && stream.url == item.url) {
                selectedItem?.selected = false
                item.requestFocus = requestFocus
                item.selected = true
                selectedItem = item
                notifyItemChanged(index)
                return index
            }
        }
        return 0
    }
}