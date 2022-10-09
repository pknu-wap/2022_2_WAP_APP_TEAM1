package com.example.witt.presentation.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignInBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.signup.SignUpEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val viewModel : SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dataBinding viewModel
        binding.viewModel = viewModel

        initButton()
        initChannel()

    }
    private fun initButton(){
        binding.signInButton.setOnClickListener {
            viewModel.onEvent(SignUpEvent.Submit)
        }

        binding.goToSignUpButton.setOnClickListener{
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initChannel(){
        lifecycleScope.launch{
            viewModel.signInEvents.collect { event ->
                when(event){
                    is SignInViewModel.SignInUiEvent.Success ->{
                        val direction = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                        findNavController().navigate(direction)
                    }
                    is SignInViewModel.SignInUiEvent.Failure ->{
                        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}