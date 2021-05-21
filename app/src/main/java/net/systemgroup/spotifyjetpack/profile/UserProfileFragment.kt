package net.systemgroup.spotifyjetpack.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import net.systemgroup.spotifyjetpack.databinding.FragmentProfileBinding


@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, {
            binding.txtFollowers.text = "${it.followers.total} Followers"
            binding.displayName.text = it.display_name
            binding.txtUri.text = it.id
            if(it.images.isNotEmpty()) {
                Glide.with(binding.avatar).load(it.images[0].url).into(binding.avatar)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}