package net.systemgroup.spotifyjetpack.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import net.systemgroup.spotifyjetpack.db.UserDao
import net.systemgroup.spotifyjetpack.model.User
import net.systemgroup.spotifyjetpack.network.ApiService
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val executor: Executor,
    private val userDao: UserDao
) {
     fun getUser(userId: String): Flow<User> {
         runBlocking(executor.asCoroutineDispatcher()) {
             refreshUser(userId)
         }
         return userDao.load(userId)
    }

    private suspend fun refreshUser(userId: String) {
        val userExists = userDao.hasUser(System.currentTimeMillis() - FRESH_TIMEOUT)
        if (!userExists) {
            try {
                val response = apiService.getUser(userId)
                response.refreshTime = System.currentTimeMillis()
                userDao.save(response)
            } catch (exception : Exception) {
                Timber.e(exception)
            }
        }
    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}