package com.tevibox.tvplay.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.tevibox.tvplay.core.entity.Stream

class StreamDiffUtilCallback(
    private val oldList: List<Stream>,
    private val newList: List<Stream>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.selected == newItem.selected
                && oldItem.favorite == newItem.favorite
    }

}