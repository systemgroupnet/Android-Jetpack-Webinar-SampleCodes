package net.systemgroup.spotifyjetpack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import net.systemgroup.spotifyjetpack.authenticator.TokenProvider
import net.systemgroup.spotifyjetpack.databinding.ActivityMainBinding
import net.systemgroup.spotifyjetpack.profile.UserProfileFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1337
    private val CLIENT_ID = "a5096928fc2c4a2fb226f87c77c3025f" //change to your Client ID
    private val REDIRECT_URI = "http://net.systemgroup.spotifyjetpack/callback" //change to your redirect
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var tokenProvider: TokenProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        com.spotify.sdk.android.auth.BuildConfig.DEBUG

        val builder: AuthorizationRequest.Builder =
            AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf("user-read-private","user-read-email"))
        val request: AuthorizationRequest = builder.build()

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response: AuthorizationResponse =
                AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    Toast.makeText(this,"Token ${response.accessToken}",Toast.LENGTH_LONG).show()
                    tokenProvider.setToken(response.accessToken)
                    supportFragmentManager.beginTransaction().replace(R.id.container,
                        UserProfileFragment()
                    ).commit()
                }
                AuthorizationResponse.Type.ERROR -> {
                    Toast.makeText(this,"Error : ${response.error}",Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this,"Else!!! ${response.code}",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}