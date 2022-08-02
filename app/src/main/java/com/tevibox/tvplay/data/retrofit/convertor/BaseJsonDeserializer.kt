package com.tevibox.tvplay.data.retrofit.convertor

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tevibox.tvplay.core.exception.InvalidJsonException
import com.tevibox.tvplay.core.exception.ResponseException
import java.lang.reflect.Type

abstract class BaseJsonDeserializer<E> : JsonDeserializer<E> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): E {
        val obj = json?.asJsonObject ?: throw InvalidJsonException()
        val errorCode = obj[ERROR_KEY].asInt
        return if (errorCode == 0) {
            deserializeJson(obj, typeOfT, context)
        } else {
            val msg = obj[MSG_KEY].asString
            throw ResponseException(errorCode, msg)
        }
    }

    abstract fun deserializeJson(
        json: JsonObject,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): E

    companion object {
        const val ERROR_KEY = "error"
        const val MSG_KEY = "msg"
    }
}