package com.example.witt.presentation.ui.profile_edit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentProfileEditBinding
import com.example.witt.presentation.base.BaseFragment


class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel : ProfileEditViewModel by viewModels()

    /*private val cropActivityResultContract = object: ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1, 1)
                .getIntent(requireActivity())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        /*cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let{ uri ->
                binding.profileImage.setImageURI(uri)
            }
        }*/

        initButton()
    }

    private fun initButton(){

        /*binding.profileImage.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }*/

        binding.profileEditButton.setOnClickListener{
            val direction = ProfileEditFragmentDirections.actionProfileEditFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

}