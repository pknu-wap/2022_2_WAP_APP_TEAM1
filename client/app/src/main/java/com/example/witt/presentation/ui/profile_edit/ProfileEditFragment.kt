package com.example.witt.presentation.ui.profile_edit

import android.os.Bundle

import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentProfileEditBinding
import com.example.witt.presentation.base.BaseFragment


class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener{
            val direction = ProfileEditFragmentDirections.actionProfileEditFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

}