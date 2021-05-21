package net.systemgroup.spotifyjetpack.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.systemgroup.spotifyjetpack.network.ServiceInterceptor
import net.systemgroup.spotifyjetpack.Constants
import net.systemgroup.spotifyjetpack.network.ApiService
import net.systemgroup.spotifyjetpack.network.HttpLogger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    open fun provideInterceptors(
        serviceInterceptor: ServiceInterceptor
    ): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        interceptors.add(HttpLogger())
        interceptors.add(serviceInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        with(clientBuilder) {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            if (interceptors.isNotEmpty()) {
                interceptors.forEach { interceptor ->
                    addInterceptor(interceptor)
                }
            }
        }

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}