package net.systemgroup.spotifyjetpack.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.systemgroup.spotifyjetpack.db.UserDao
import net.systemgroup.spotifyjetpack.db.UserDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): UserDatabase {
        return Room.databaseBuilder(
            appContext,
            UserDatabase::class.java,
            "user.db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao()
    }
}