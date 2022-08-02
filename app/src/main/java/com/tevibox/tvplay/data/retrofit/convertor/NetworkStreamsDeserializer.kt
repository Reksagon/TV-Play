package com.tevibox.tvplay.data.retrofit.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.tevibox.tvplay.core.exception.InvalidJsonException
import com.tevibox.tvplay.core.exception.ResponseException
import com.tevibox.tvplay.data.retrofit.entity.NetworkStream
import com.tevibox.tvplay.data.retrofit.entity.NetworkStreams
import java.lang.reflect.Type

class NetworkStreamsDeserializer : JsonDeserializer<NetworkStreams> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkStreams {
        val obj = json?.asJsonObject ?: throw InvalidJsonException()
        val errorCode = obj["error"].asInt
        return if (errorCode == 0) {
            val jsonStreams = obj["streams"].asJsonObject
            val mapType = object : TypeToken<Map<String, NetworkStream>>() {}.type
            val streams = context?.deserialize<Map<String, NetworkStream>>(jsonStreams, mapType)
                ?: throw InvalidJsonException()

            val time = obj["time"].asLong
            NetworkStreams(errorCode, streams, time)
//            throw UnsupportedOperationException()
//            context?.deserialize<NetworkStreams>(json, typeOfT) ?: throw InvalidJsonException()
        } else {
            val msg = obj["msg"].asString
            throw ResponseException(errorCode, msg)
        }
    }
}