package net.systemgroup.spotifyjetpack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.systemgroup.spotifyjetpack.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}