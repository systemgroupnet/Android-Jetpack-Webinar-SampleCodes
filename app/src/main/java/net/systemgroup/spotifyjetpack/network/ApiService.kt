package net.systemgroup.spotifyjetpack.network

import kotlinx.coroutines.flow.Flow
import net.systemgroup.spotifyjetpack.model.User
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("v1/users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): User

}