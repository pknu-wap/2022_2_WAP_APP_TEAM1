package com.example.witt.presentation.ui.signup
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignUpBinding
import com.example.witt.presentation.base.BaseFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToSignUp2Button.setOnClickListener{
            val direction = SignUpFragmentDirections.actionSignUpFragmentToSignUp2Fragment()
            findNavController().navigate(direction)
        }
    }

}