package net.systemgroup.spotifyjetpack.authenticator

class DefaultTokenProvider() : TokenProvider {
    private var token: String? = null
    override fun setToken(token: String) {
        this.token = token
    }


    override fun getToken() = this.token
}