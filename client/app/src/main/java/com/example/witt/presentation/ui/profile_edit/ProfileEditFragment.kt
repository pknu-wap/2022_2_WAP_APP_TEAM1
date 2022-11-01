package com.example.witt.presentation.ui.profile_edit

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.canhub.cropper.*
import com.example.witt.R
import com.example.witt.databinding.FragmentProfileEditBinding
import com.example.witt.presentation.base.BaseFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel : ProfileEditViewModel by viewModels()
    private val prefs: SharedPreferences by lazy { requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)}

    //image cropping
    private val cropImage = registerForActivityResult(CropImageContract()){ result ->
        if(result.isSuccessful){
            val imageUri = result.getUriFilePath(requireContext())
            getPathFromLocalUri(imageUri)
            uploadImage(imageUri)
        }else{
            Toast.makeText(requireContext(), result.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //permission listener
    private val permissionListener = object: PermissionListener{
        override fun onPermissionGranted() {
            cropCircleImage()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(requireContext(), "설정에서 권한을 다시 설정할 수 있습니다.", Toast.LENGTH_SHORT).show()
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

    private fun initButton() {
        binding.profileImage.setOnClickListener {
            permissionCheck()
        }

        binding.profileEditButton.setOnClickListener {
            viewModel.onEvent(ProfileEditEvent.SubmitProfile)
        }
    }

    private fun permissionCheck() {
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    private fun cropCircleImage(){
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setAspectRatio(1, 1)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    private fun initProfile(){
        val nickName =  prefs.getString("nickName", null)
        val profile = prefs.getString("profile", null)
        initNickNameTextView(nickName)
        getPathFromRemoteUri(profile)
        uploadImage(profile)
    }

    private fun initNickNameTextView(nickName: String?){
        nickName?.let {
            binding.nameEditText.setText(nickName)
            viewModel.onEvent(ProfileEditEvent.SubmitNickName(nickName))
        }
    }

    private fun getPathFromRemoteUri(imageUrl: String?){
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val file = File(requireContext().cacheDir, "profile.jpg")
                if (!imageUrl.isNullOrBlank()) {//프로필 이미지가 등록되어 있는 경우
                    val url = URL(imageUrl)
                    val stream = url.openStream()
                    file.outputStream().use{
                        stream.copyTo(it)
                    }
                } else {
                    file.outputStream().use { //아닌 경우
                        requireContext().assets.open("default_profile.jpg").copyTo(it) //assets의 기본 이미지 저장후 가져오기
                    }
                }
                viewModel.onEvent(ProfileEditEvent.SubmitProfileImage(file))
            }
        }
    }

    private fun getPathFromLocalUri(imageUri: String?){
        // uri이 두가지, 하나는 인터넷의 uri, 하나는 내 파일 속 uri
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { //createNewFile, 에셋 열기에서 exception 발생가능성 있음.
                val file = File(requireActivity().cacheDir, "profile.jpg")
                file.createNewFile()  //캐시 디렉토리에 프로파일 이미지 파일 생성
                if (!imageUri.isNullOrBlank()) {//프로필 이미지가 등록되어 있는 경우
                    file.outputStream().use {
                        File(imageUri).inputStream().copyTo(it)
                    }
                } else {
                    file.outputStream().use { //아닌 경우
                        requireContext().assets.open("default_profile.jpg").copyTo(it) //assets의 기본 이미지 저장후 가져오기
                    }
                }
                viewModel.onEvent(ProfileEditEvent.SubmitProfileImage(file))
            }
        }
    }

    private fun uploadImage(imageUri: String?){
        imageUri?.let{
            Glide.with(requireActivity())
                .load(imageUri)
                .placeholder(R.drawable.penguin)
                .into(binding.profileImage)
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
}