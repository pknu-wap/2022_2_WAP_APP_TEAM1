package com.example.witt.presentation.ui.profile_edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.canhub.cropper.*
import com.example.witt.R
import com.example.witt.databinding.FragmentProfileEditBinding
import com.example.witt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel : ProfileEditViewModel by viewModels()
    private val arg: ProfileEditFragmentArgs by navArgs()

    private val cropImage = registerForActivityResult(CropImageContract()){ result ->
        if(result.isSuccessful){
            uploadProfileImage(imageUri = result.getUriFilePath(requireActivity()))
        }else{
            Toast.makeText(requireActivity(), result.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initButton()
        initProfile()
        initChannel()
        initError()

    }

    private fun initButton(){

        binding.profileImage.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                    setAspectRatio(1, 1)
                    setCropShape(CropImageView.CropShape.OVAL)
                }
            )
        }

        binding.profileEditButton.setOnClickListener{
            viewModel.onEvent(ProfileEditEvent.SubmitProfile)
        }
    }

    private fun initChannel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { //repeated Life Cycle
                viewModel.profileEditEvents.collect { event ->
                    when (event) {
                        is ProfileEditViewModel.ProfileEditUiEvent.Failure -> {
                            Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
                        }
                        is ProfileEditViewModel.ProfileEditUiEvent.Success -> {
                            val direction =
                                ProfileEditFragmentDirections.actionProfileEditFragmentToHomeFragment()
                            findNavController().navigate(direction)
                        }
                    }
                }
            }
        }
    }

    private fun initError(){
        viewModel.errorNickName.observe(viewLifecycleOwner){ errorMessage ->
            binding.nameEditText.error = errorMessage
        }
        viewModel.errorPhoneNum.observe(viewLifecycleOwner){ errorMessage ->
            binding.phoneNumberEditText.error = errorMessage
        }
    }

    //todo arg 처리하기
    private fun initProfile(){
        viewModel.inputName.postValue(arg.nickName)
        uploadProfileImage(arg.profileImage)
    }

    private fun uploadProfileImage(imageUri: String?){
        imageUri?.let{
            Glide.with(requireActivity())
                .load(imageUri)
                .placeholder(R.drawable.penguin)
                .into(binding.profileImage)
            viewModel.onEvent(ProfileEditEvent.SubmitProfileImage(imageUri))
        }
    }
}