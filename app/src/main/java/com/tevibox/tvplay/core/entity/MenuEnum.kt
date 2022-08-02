package com.tevibox.tvplay.core.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tevibox.tvplay.R

enum class MenuEnum(
    @DrawableRes val iconId: Int,
    @StringRes val titleId: Int
) {

    MY_ACCOUNT(R.drawable.ic_outline_account_circle_24, R.string.player_menu_item_account),
    CHANNEL_LIST(R.drawable.ic_baseline_menu_24, R.string.player_menu_item_channel_list),

    //    SEARCH(R.drawable.ic_baseline_search_24, R.string.player_menu_item_search),
    FAVORITE(R.drawable.ic_baseline_star_outline_24, R.string.player_menu_item_favorite),
    LOCK(R.drawable.ic_outline_lock_24, R.string.player_menu_item_lock),

    //    SUBSCRIBE_LIST(R.drawable.ic_baseline_alarm_24, R.string.player_menu_item_subscribe_list),
//    INFO(R.drawable.ic_outline_info_24, R.string.player_menu_item_info),
//    MOBILE_APP(R.drawable.ic_outline_phone_android_24, R.string.player_menu_item_mobile),
//    NOTIFICATION(R.drawable.ic_outline_notifications_24, R.string.player_menu_item_notification);
}