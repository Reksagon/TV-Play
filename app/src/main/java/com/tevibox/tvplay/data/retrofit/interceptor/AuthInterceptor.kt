package com.tevibox.tvplay.data.retrofit.interceptor

import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.domain.TokenStore
import com.tevibox.tvplay.domain.repository.UserTokenRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.java.KoinJavaComponent.get
import timber.log.Timber

class AuthInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("intercept()")
        val request = chain.request()
        val newRequest = buildNewRequest(request)
        return chain.proceed(newRequest)
    }

    private fun buildNewRequest(oldRequest: Request) = runBlocking {
        Timber.d("buildNewRequest")
        if (tokenStore.isTokenExpired()) refreshToken(tokenStore.getToken())

        val token = tokenStore.getToken()

        val builder = oldRequest.newBuilder()
            .addHeader(X_LU_AUTH_NAME, token.hash)

        return@runBlocking builder.build()
    }

    private fun refreshToken(token: UserToken) = runBlocking {
        Timber.d("refreshToken()")
        get<UserTokenRepository>(clazz = UserTokenRepository::class.java).renew(
            token.hash,
            tokenStore.requireDeviceInformation()
        ).collect(tokenStore::setToken)
    }

    companion object {
        private const val X_LU_AUTH_NAME = "X-LU-AUTH"
    }
}