package net.systemgroup.spotifyjetpack.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.systemgroup.spotifyjetpack.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    userRepository: UserRepository) : ViewModel() {
    val user = userRepository.getUser("tuggareutangranser").asLiveData()
}