package com.example.witt.presentation.ui.profile_edit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.di.IoDispatcher
import com.example.witt.domain.use_case.remote.UploadRemoteProfile
import com.example.witt.domain.use_case.validate.ValidateNickName
import com.example.witt.domain.use_case.validate.ValidatePhoneNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val uploadRemoteProfile: UploadRemoteProfile,
    private val application : Application,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    private val validateNickName by lazy { ValidateNickName() }
    private val validatePhoneNum by lazy { ValidatePhoneNum() }

    val inputName : MutableLiveData<String> = MutableLiveData()
    val inputPhoneNumber : MutableLiveData<String> = MutableLiveData()

    //수정 필요,, 이미지 양방향 확인해야할듯, 기본 이미지도 초기화 해줘야할듯
    private val inputImage: MutableLiveData<String> = MutableLiveData()


    private val _errorNickName : MutableLiveData<String> = MutableLiveData()
    val errorNickName : LiveData<String> get() = _errorNickName

    private val _errorPhoneNum : MutableLiveData<String> = MutableLiveData()
    val errorPhoneNum : LiveData<String> get() = _errorPhoneNum

    private val profileEditChannel = Channel<ProfileEditUiEvent>()
    val profileEditEvents = profileEditChannel.receiveAsFlow()

    fun onEvent(event: ProfileEditEvent){
        when(event){
            is ProfileEditEvent.SubmitProfile ->{
                validateData()
            }
            is ProfileEditEvent.SubmitProfileImage ->{
                //자른 이미지 liveData Mapping
                inputImage.value = event.profileImage
            }
        }
    }

    private fun validateData(){
        val nickNameResult = validateNickName.execute(inputName.value ?: "")
        val phoneNumResult = validatePhoneNum.execute(inputPhoneNumber.value ?: "")

        val hasError = listOf(
            nickNameResult,
            phoneNumResult
        ).any{
            !it.successful
        }
        if(hasError){
            nickNameResult.errorMessage?.let { errorMessage ->
                _errorNickName.value = errorMessage
            }
            phoneNumResult.errorMessage?.let{ errorMessage ->
                _errorPhoneNum.value = errorMessage
            }
            return
        }
        submitProfile()
    }

    private fun submitProfile(){
        viewModelScope.launch(coroutineDispatcher) {
            //todo refactor : 소셜 로그인을 위한 양방향 이미지 처리 구현 및 nullSafety 구현 완벽히
            if(!inputName.value.isNullOrBlank() && !inputPhoneNumber.value.isNullOrBlank() && !inputImage.value.isNullOrBlank()){
                val profileFile = File(inputImage.value!!)
                val file = File(application.cacheDir, "profile.jpg")
                file.createNewFile()
                file.outputStream().use {
                    profileFile.inputStream().copyTo(it)
                }

                val response = uploadRemoteProfile(file, inputName.value ?: "", inputPhoneNumber.value ?: "")

                response.mapCatching {
                    if(it.status){
                        profileEditChannel.trySend(ProfileEditUiEvent.Success)
                    }else{
                        profileEditChannel.trySend(ProfileEditUiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    profileEditChannel.trySend(ProfileEditUiEvent.Failure("네트워크를 확인해주세요."))
                }
            }
            else {
                profileEditChannel.trySend(ProfileEditUiEvent.Failure("빈칸을 확인해주세요."))
            }
        }
    }

    sealed class ProfileEditUiEvent{
        data class Failure(val message: String?): ProfileEditUiEvent()
        object Success: ProfileEditUiEvent()
    }
}