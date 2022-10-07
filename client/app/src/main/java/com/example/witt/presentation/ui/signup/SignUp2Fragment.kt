package com.example.witt.presentation.ui.signup

import android.os.Bundle

import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignUp2Binding
import com.example.witt.presentation.base.BaseFragment


class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(R.layout.fragment_sign_up2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener{
            val direction = SignUp2FragmentDirections.actionSignUp2FragmentToSignInFragment()
            findNavController().navigate(direction)
        }
    }

}