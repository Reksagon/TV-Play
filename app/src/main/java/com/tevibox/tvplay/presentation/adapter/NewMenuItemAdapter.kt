package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.databinding.LayoutMenuItemBinding

class NewMenuItemAdapter(
    private val onItemClick: (MenuItem) -> Unit
) : BaseSelectableAdapter<MenuItem, LayoutMenuItemBinding, NewMenuItemViewHolder>() {

    override fun onCreateSelectableViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NewMenuItemViewHolder(parent)

    override fun onItemClicked(item: MenuItem) {
        if (item.selected) return
        super.onItemClicked(item)
        onItemClick(item)
    }

    fun deselectItem() {
        selectedItem?.selected = false
        items.forEachIndexed { index, menuItem ->
            if (menuItem.id == selectedItem?.id) {
                notifyItemChanged(index)
                return@forEachIndexed
            }
        }
        selectedItem = null
    }

    fun selectItemById(id: Int) {
        items.forEach { menuItem ->
            if (menuItem.id == id) {
                onItemClicked(menuItem)
                return
            }
        }
    }
}