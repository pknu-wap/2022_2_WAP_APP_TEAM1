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

    private val profileImageUri: MutableLiveData<String> = MutableLiveData()

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
                profileImageUri.postValue(event.profileImage)
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
            val file = File(application.cacheDir, "profile.jpg")
            runCatching { //createNewFile, 에셋 열기에서 exception 발생가능성 있음.
                file.createNewFile()  //캐시 디렉토리에 프로파일 이미지 파일 생성
                if(!profileImageUri.value.isNullOrBlank()) {//프로필 이미지가 등록되어 있는 경우
                    file.outputStream().use {
                        File(profileImageUri.value!!).inputStream().copyTo(it)
                    }
                }
                else {
                    file.outputStream().use { //아닌 경우
                        application.assets.open("default_profile.jpg")
                            .copyTo(it) //assets의 기본 이미지 저장후 가져오기
                    }
                }
            }.onFailure {
                profileEditChannel.trySend(ProfileEditUiEvent.Failure("프로필 이미지를 저장하는데 실패하였습니다."))
            }

            val response =
                uploadRemoteProfile(file, inputName.value ?: "", inputPhoneNumber.value ?: "")

            response.mapCatching {
                if (it.status) {
                    profileEditChannel.trySend(ProfileEditUiEvent.Success)
                } else {
                    profileEditChannel.trySend(ProfileEditUiEvent.Failure(it.reason))
                }
            }.onFailure {
                profileEditChannel.trySend(ProfileEditUiEvent.Failure("네트워크를 확인해주세요."))
            }
        }
    }


    sealed class ProfileEditUiEvent{
        data class Failure(val message: String?): ProfileEditUiEvent()
        object Success: ProfileEditUiEvent()
    }
}