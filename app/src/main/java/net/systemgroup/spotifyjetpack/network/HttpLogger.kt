package net.systemgroup.spotifyjetpack.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException


class HttpLogger : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Timber.i("REQUEST URL      => ${request.url()}")
        Timber.i("REQUEST METHOD   => ${request.method()}")
        Timber.i("REQUEST HEADER   => ${request.headers()}")
        Timber.i("REQUEST BODY     => ${bodyToString(request)}")

        val response = chain.proceed(request)

        val responseBody = response.body()
        val hasRequestBody = responseBody != null
        if(hasRequestBody) {
            val responseBodyString = responseBody!!.string()
            val newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.toByteArray())).build()
            Timber.i( "RESPONSE BODY    => $responseBodyString")
            return newResponse
        }
        return response

    }

    private fun bodyToString(request: Request): String {

        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: Exception) {
            return "no Body"
        }

    }
}