package com.tevibox.tvplay.di

import com.google.gson.GsonBuilder
import com.tevibox.tvplay.BuildConfig
import com.tevibox.tvplay.data.retrofit.AuthApiService
import com.tevibox.tvplay.data.retrofit.UnAuthApiService
import com.tevibox.tvplay.data.retrofit.convertor.NetworkActivationCodeDeserializer
import com.tevibox.tvplay.data.retrofit.convertor.NetworkStreamsDeserializer
import com.tevibox.tvplay.data.retrofit.convertor.NetworkTokenDeserializer
import com.tevibox.tvplay.data.retrofit.convertor.NetworkUserDeserializer
import com.tevibox.tvplay.data.retrofit.entity.NetworkActivationCode
import com.tevibox.tvplay.data.retrofit.entity.NetworkStreams
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser
import com.tevibox.tvplay.data.retrofit.entity.NetworkUserToken
import com.tevibox.tvplay.data.retrofit.interceptor.AuthInterceptor
import com.tevibox.tvplay.data.retrofit.interceptor.StaticHeadersInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val HOST_URL = "https://a1.tevibox.com/"

const val HTTP_CONNECTION_TIMEOUT = 30L

const val NO_AUTH_API = "NO_AUTH_API"

const val AUTH_API = "AUTH_API"

fun Module.retrofitModule() {

    single {
        HttpLoggingInterceptor(Timber::d).also {
            it.level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }
    }

    single {
        CertificatePinner.Builder()
            .build()
    }

    single { AuthInterceptor(get()) }

    single(named(NO_AUTH_API)) {
        OkHttpClient().newBuilder()
            .certificatePinner(get())
            .retryOnConnectionFailure(true)
            .addInterceptor(StaticHeadersInterceptor())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single(named(AUTH_API)) {
        OkHttpClient().newBuilder()
            .certificatePinner(get())
            .retryOnConnectionFailure(true)
            .addInterceptor(StaticHeadersInterceptor())
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonBuilder().registerTypeAdapter(
            NetworkUserToken::class.java, NetworkTokenDeserializer()
        ).registerTypeAdapter(
            NetworkStreams::class.java, NetworkStreamsDeserializer()
        ).registerTypeAdapter(
            NetworkUser::class.java, NetworkUserDeserializer()
        ).registerTypeAdapter(
            NetworkActivationCode::class.java, NetworkActivationCodeDeserializer()
        ).create()
    }

    single(named(NO_AUTH_API)) {
        Retrofit.Builder()
            .client(get(named(NO_AUTH_API)))
            .baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single(named(AUTH_API)) {
        Retrofit.Builder()
            .client(get(named(AUTH_API)))
            .baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>(named(NO_AUTH_API)).create(UnAuthApiService::class.java) }

    single { get<Retrofit>(named(AUTH_API)).create(AuthApiService::class.java) }
}