package com.example.witt.presentation.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignInBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.signup.SignUpEvent
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val viewModel : SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //후에 점검이 필요한 코드 data binding viewModel 연결 및 lifeCycleOwner 정의
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launch{
            viewModel.signInEvents.collect { event ->
                when(event){
                    is SignInViewModel.SignInUiEvent.Success ->{
                        val direction = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                        findNavController().navigate(direction)
                    }
                    is SignInViewModel.SignInUiEvent.Failure ->{
                        Log.d("tag", "error")
                    }
                }
            }
        }
        //Error 표시
        viewModel.errorEmail.observe(viewLifecycleOwner){
            binding.emailEditTextView.error = it
        }
        viewModel.errorPassword.observe(viewLifecycleOwner){
            binding.passwordEditText.error = it
        }

        binding.signInButton.setOnClickListener {
            viewModel.onEvent(SignUpEvent.SubmitEmailPassword)
        }

        binding.goToSignUpButton.setOnClickListener{
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }
    }
}