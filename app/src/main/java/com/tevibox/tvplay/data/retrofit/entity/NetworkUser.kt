package com.tevibox.tvplay.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class NetworkUser(
    @SerializedName(ACCOUNT_KEY) val email: String,
    @SerializedName(SUBSCRIPTION_STATUS_KEY) val subscriptionStatus: String,
    @SerializedName(SUBSCRIPTION_EXPIRE_KEY) val subscriptionExpire: Long,
    @SerializedName(CARD_KEY) val card: String
) {

    companion object {

        const val ACCOUNT_KEY = "account"

        const val SUBSCRIPTION_STATUS_KEY = "subscription_status"

        const val SUBSCRIPTION_EXPIRE_KEY = "subscription_expire"

        const val CARD_KEY = "card"
    }
}
