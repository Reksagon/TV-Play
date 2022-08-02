package com.tevibox.tvplay.core.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MenuItem(
    override val id: Int,
    @DrawableRes val icon: Int,
    @StringRes val titleId: Int,
    override var selected: Boolean = false
) : SelectableEntityInterface
