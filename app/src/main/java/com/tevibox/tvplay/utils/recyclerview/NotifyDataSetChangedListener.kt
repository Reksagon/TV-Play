package com.tevibox.tvplay.utils.recyclerview

import androidx.recyclerview.widget.RecyclerView

class NotifyDataSetChangedListener(
    private val onDataChanged: () -> Unit
) : RecyclerView.AdapterDataObserver() {

    override fun onChanged() = onDataChanged()
}