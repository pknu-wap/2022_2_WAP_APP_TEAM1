package com.example.witt.presentation.ui.profile_edit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.canhub.cropper.*
import com.example.witt.R
import com.example.witt.databinding.FragmentProfileEditBinding
import com.example.witt.presentation.base.BaseFragment


class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel : ProfileEditViewModel by viewModels()
    private val arg: ProfileEditFragmentArgs by navArgs()

    private val cropImage = registerForActivityResult(CropImageContract()){ result ->
        if(result.isSuccessful){
            Glide.with(requireActivity())
                .load(result.uriContent)
                .into(binding.profileImage)
        }else{
            Toast.makeText(requireActivity(), result.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initButton()
        initProfile()
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
            val direction = ProfileEditFragmentDirections.actionProfileEditFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initProfile(){
        Log.d("arg",arg.nickName.toString())
        Log.d("arg",arg.profileImage.toString())
        viewModel.inputName.postValue(arg.nickName.toString())
        Glide.with(requireActivity())
            .load(arg.profileImage.toString())
            .into(binding.profileImage)
    }
}