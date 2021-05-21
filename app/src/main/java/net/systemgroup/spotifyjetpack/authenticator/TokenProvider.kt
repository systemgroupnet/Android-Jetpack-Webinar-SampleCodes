package net.systemgroup.spotifyjetpack.authenticator

interface TokenProvider {
    fun setToken(token: String)
    fun getToken(): String?
}