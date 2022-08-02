package com.tevibox.tvplay.data.retrofit.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser.Companion.ACCOUNT_KEY
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser.Companion.CARD_KEY
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser.Companion.SUBSCRIPTION_EXPIRE_KEY
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser.Companion.SUBSCRIPTION_STATUS_KEY
import java.lang.reflect.Type

class NetworkUserDeserializer : BaseJsonDeserializer<NetworkUser>() {

    override fun deserializeJson(
        json: JsonObject,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkUser {
        val info = json["info"].asJsonObject

        val email = info[ACCOUNT_KEY].asString
        val status = info[SUBSCRIPTION_STATUS_KEY].asString
        val expire = try {
            info[SUBSCRIPTION_EXPIRE_KEY].asLong
        } catch (exception: Exception) {
            -1
        }
        val card = info[CARD_KEY].asString

        return NetworkUser(email, status, expire, card)
    }
}