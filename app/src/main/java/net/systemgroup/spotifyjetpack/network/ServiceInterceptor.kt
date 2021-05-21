package net.systemgroup.spotifyjetpack.network

import net.systemgroup.spotifyjetpack.authenticator.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ServiceInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        tokenProvider.getToken()?.let { nonNullToken ->
            val ongoing = chain.request().newBuilder()
            ongoing.addHeader("Authorization", "Bearer $nonNullToken")
            ongoing.addHeader("Content-Type", "application/json; charset=utf-8")
            return chain.proceed(ongoing.build())
        }
        return chain.proceed(chain.request())
    }
}