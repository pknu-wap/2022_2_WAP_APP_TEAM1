package com.example.witt.presentation.ui.profile_edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.use_case.local.GetLocalProfile
import com.example.witt.domain.use_case.local.SetLocalProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getLocalProfile: GetLocalProfile,
    private val setLocalProfile: SetLocalProfile
): ViewModel() {

    val inputName : MutableLiveData<String> = MutableLiveData()
    val inputPhoneNumber : MutableLiveData<String> = MutableLiveData()
    //수정 필요,, 이미지 양방향 확인해야할듯 하,,
    val inputImage: MutableLiveData<String> = MutableLiveData()

    private val profileEditChannel = Channel<ProfileEditUiEvent>()
    val profileEditEvents = profileEditChannel.receiveAsFlow()

    fun onEvent(event: ProfileEditEvent){
        when(event){
            is ProfileEditEvent.SubmitProfile ->{
                submitProfile()
            }
            is ProfileEditEvent.GetProfile ->{
                getProfile()
            }
            is ProfileEditEvent.SubmitProfileImage ->{
                //자른 이미지 liveData Mapping
                inputImage.value = event.profileImage
            }
        }
    }
    private fun submitProfile(){
        viewModelScope.launch {
            //내부 데이터 베이스 추가 및 네트워크로 보내기 async 활용
            if(!inputImage.value.isNullOrBlank() && !inputName.value.isNullOrBlank()){
                val result = setLocalProfile(
                    inputImage.value ?: "", inputName.value ?: ""
                ).isSuccess
                if(result){
                    profileEditChannel.trySend(ProfileEditUiEvent.SuccessToSetProfile)
                }
            }
        }
    }
    private fun getProfile(){
        viewModelScope.launch {
            getLocalProfile().collectLatest { result ->
                result.mapCatching {
                    profileEditChannel.trySend(ProfileEditUiEvent.SuccessToGetProfile(
                        it.profileUri,
                        it.userName
                    ))
                }
            }
        }
    }

    sealed class ProfileEditUiEvent{
        data class Failure(val message: String?): ProfileEditUiEvent()
        data class SuccessToGetProfile(val profileUri: String, val userName: String): ProfileEditUiEvent()
        object SuccessToSetProfile : ProfileEditUiEvent()
    }
}