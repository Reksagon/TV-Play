package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.javavirys.core.presentation.adapter.BaseAdapter
import com.tevibox.tvplay.core.entity.SelectableEntityInterface

abstract class BaseSelectableAdapter<E : SelectableEntityInterface, VB : ViewDataBinding, VH : BaseSelectableViewHolder<E, VB>>
    : BaseAdapter<E, VB, VH>() {

    protected var selectedItem: E? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = onCreateSelectableViewHolder(parent, viewType)
        viewHolder.onItemClick = ::onItemClicked
        viewHolder.onItemFocused = ::onItemFocused

        return viewHolder
    }

    @CallSuper
    protected open fun onItemClicked(item: E) {
        if (item.id == selectedItem?.id) return

        item.selected = true

        selectedItem?.selected = false

        items.forEachIndexed { index, menuItem ->
            if (menuItem.id == item.id || menuItem.id == selectedItem?.id) {
                notifyItemChanged(index)
            }
        }

        selectedItem = item
    }

    protected open fun onItemFocused(item: E) {
        // none
    }

    abstract fun onCreateSelectableViewHolder(parent: ViewGroup, viewType: Int): VH
}