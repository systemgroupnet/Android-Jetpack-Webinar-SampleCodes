package net.systemgroup.spotifyjetpack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.systemgroup.spotifyjetpack.model.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun load(userId: String): Flow<User>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE refreshTime > :freshTimeout)")
    fun hasUser(freshTimeout: Long): Boolean
}