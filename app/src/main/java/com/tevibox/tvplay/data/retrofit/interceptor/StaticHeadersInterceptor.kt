package com.tevibox.tvplay.data.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class StaticHeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader(API_KEY_NAME, API_KEY)

        return chain.proceed(builder.build())
    }

    companion object {

        private const val API_KEY_NAME = "X-LU-API-KEY"

        private const val API_KEY =
            "caa246c938c78daa12894d63d03e17378e85c05d154e0d9b183c9d9c8947a789"
    }
}