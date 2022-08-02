package com.tevibox.tvplay.data.retrofit.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.tevibox.tvplay.core.exception.InvalidJsonException
import com.tevibox.tvplay.core.exception.ResponseException
import com.tevibox.tvplay.data.retrofit.entity.NetworkUserToken
import java.lang.reflect.Type

class NetworkTokenDeserializer : JsonDeserializer<NetworkUserToken> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkUserToken {
        val obj = json?.asJsonObject ?: throw InvalidJsonException()
        val errorCode = obj["error"].asInt
        return if (errorCode == 0) {
            val hash = obj["hash"].asString
            var expire = -1L

            try {
                expire = obj["expire"].asLong
            } catch (exception: Exception) {
            }

            val time = obj["time"].asLong
            NetworkUserToken(0, hash, expire, time)
        } else {
            val msg = obj["msg"].asString
            throw ResponseException(errorCode, msg)
        }
    }
}