package net.systemgroup.spotifyjetpack.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.systemgroup.spotifyjetpack.authenticator.DefaultTokenProvider
import net.systemgroup.spotifyjetpack.authenticator.TokenProvider
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class
    ]
)
object AppModule {
    @Provides
    @Singleton
    fun provideTokenProvider(): TokenProvider {
        return DefaultTokenProvider()
    }

    @Provides
    fun provideExecutor() : Executor {
        return Executors.newSingleThreadExecutor()
    }
}