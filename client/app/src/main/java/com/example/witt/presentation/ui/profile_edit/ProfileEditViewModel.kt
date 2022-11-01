package com.example.witt.presentation.ui.profile_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.use_case.remote.UploadRemoteProfile
import com.example.witt.domain.use_case.validate.ValidateNickName
import com.example.witt.domain.use_case.validate.ValidatePhoneNum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val uploadRemoteProfile: UploadRemoteProfile,
): ViewModel() {

    private val validateNickName by lazy { ValidateNickName() }
    private val validatePhoneNum by lazy { ValidatePhoneNum() }

    val inputName : MutableLiveData<String> = MutableLiveData()
    val inputPhoneNumber : MutableLiveData<String> = MutableLiveData()

    private val profileImage: MutableLiveData<File> = MutableLiveData()

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
                profileImage.postValue(event.profileImage)
            }
            is ProfileEditEvent.SubmitNickName ->{
                inputName.value = event.nickName
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
        viewModelScope.launch {
            profileImage.value?.let { profile ->
                val response =
                    uploadRemoteProfile(profile,
                        inputName.value ?: "",
                        inputPhoneNumber.value ?: "")

                response.mapCatching {
                    if (it.status) {
                        profileEditChannel.trySend(ProfileEditUiEvent.Success)
                    } else {
                        profileEditChannel.trySend(ProfileEditUiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    profileEditChannel.trySend(ProfileEditUiEvent.Failure("네트워크를 확인해주세요."))
                }
            } ?: profileEditChannel.trySend(ProfileEditUiEvent.Failure("사진을 재선택해주세요."))
        }

    }


    sealed class ProfileEditUiEvent{
        data class Failure(val message: String?): ProfileEditUiEvent()
        object Success: ProfileEditUiEvent()
    }
}