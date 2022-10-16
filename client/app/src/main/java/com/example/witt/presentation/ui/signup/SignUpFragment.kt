package com.example.witt.presentation.ui.signup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignUpBinding
import com.example.witt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val viewModel : SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dataBinding viewModel
        binding.viewModel = viewModel

        initButton()
        initError()
        initChannel()

    }

    private fun initButton(){
        binding.signUpButton.setOnClickListener{
            viewModel.onEvent(SignUpEvent.Submit)
        }
        binding.duplcateEmailButton.setOnClickListener {
            viewModel.onEvent(SignUpEvent.DuplicateEmail)
        }
    }

    private fun initError(){
        viewModel.errorEmail.observe(viewLifecycleOwner){ errorMessage ->
            binding.emailEditTextView.error = errorMessage
        }
        viewModel.errorPassword.observe(viewLifecycleOwner){ errorMessage->
            binding.passwordEditText.error = errorMessage
        }
        viewModel.errorRepeatedPassword.observe(viewLifecycleOwner){ errorMessage ->
            binding.repeatedPasswordEditText.error = errorMessage
        }
    }

    private fun initChannel(){
        lifecycleScope.launch{
            viewModel.signUpEvents.collect{ event ->
                when(event){
                    is SignUpViewModel.SignUpUiEvent.Success -> {
                        val direction = SignUpFragmentDirections.actionSignUpFragmentToProfileEditFragment("","")
                        findNavController().navigate(direction)
                    }
                    is SignUpViewModel.SignUpUiEvent.Failure ->{
                        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignUpViewModel.SignUpUiEvent.DuplicateChecked ->{
                        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}