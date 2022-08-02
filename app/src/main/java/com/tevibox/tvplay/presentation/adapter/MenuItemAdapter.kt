package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import com.javavirys.core.presentation.adapter.BaseAdapter
import com.tevibox.tvplay.core.entity.MenuItem
import com.tevibox.tvplay.databinding.LayoutMenuItemBinding

class MenuItemAdapter(
    private val onItemCLick: (MenuItem) -> Unit
) : BaseAdapter<MenuItem, LayoutMenuItemBinding, MenuItemViewHolder>() {

    private var selectedItem: MenuItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MenuItemViewHolder(
        parent,
        ::onClick
    )

    private fun onClick(item: MenuItem) {
        if (item.id == selectedItem?.id)
            return

        selectedItem?.selected = false

        items.forEachIndexed { index, menuItem ->
            if (menuItem.id == item.id || menuItem.id == selectedItem?.id) {
                notifyItemChanged(index)
            }
        }

        selectedItem = item
        onItemCLick(item)
    }
}