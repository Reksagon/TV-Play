package com.tevibox.tvplay.presentation.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.javavirys.core.presentation.adapter.BaseViewHolder
import com.tevibox.tvplay.core.entity.SelectableEntityInterface

abstract class BaseSelectableViewHolder<E : SelectableEntityInterface, VB : ViewDataBinding>(
    parent: ViewGroup,
    @LayoutRes layoutId: Int
) : BaseViewHolder<E, VB>(parent, layoutId) {

    var onItemFocused: ((E) -> Unit)? = null

    var onItemClick: ((E) -> Unit)? = null

    override fun bind(item: E) {
        binding?.let {
            itemView.isFocusableInTouchMode = true
            itemView.setOnFocusChangeListener { _, hasFocus ->
                showFocusedItem(item, hasFocus)
                if (hasFocus) {
                    onItemFocused?.invoke(item)

//                    onItemFocused(item)
                }
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }

            bind(it, item)
            showSelectedItem(item)
        }
    }


    protected open fun showSelectedItem(item: E) {
        // none
    }

    protected open fun showFocusedItem(item: E, hasFocus: Boolean) {
        // none
    }

    abstract fun bind(binding: VB, item: E)
}