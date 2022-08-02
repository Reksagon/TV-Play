package com.tevibox.tvplay.data.retrofit.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.tevibox.tvplay.data.retrofit.entity.NetworkActivationCode
import java.lang.reflect.Type

class NetworkActivationCodeDeserializer : BaseJsonDeserializer<NetworkActivationCode>() {

    override fun deserializeJson(
        json: JsonObject,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkActivationCode {
        val expire = json["expire"].asLong

        return NetworkActivationCode(expire)
    }
}