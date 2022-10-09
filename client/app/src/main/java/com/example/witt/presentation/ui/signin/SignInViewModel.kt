package com.example.witt.presentation.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.validate.ValidateEmail
import com.example.witt.domain.use_case.validate.ValidatePassword
import com.example.witt.presentation.ui.signup.SignUpEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailPassword: SignInEmailPassword
): ViewModel() {

    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()


    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.Submit ->{
                submitData()
            }
        }
    }

    private fun submitData(){
        //data 전송
        viewModelScope.launch {
            if(!inputEmail.value.isNullOrBlank() && !inputPassword.value.isNullOrBlank()){
                val signInResult =
                    signInEmailPassword(accountType = 0, inputEmail.value ?: "", inputPassword.value ?: "")
                if(signInResult.isSuccess){
                    signInEventChannel.trySend(SignInUiEvent.Success)
                }else{
                    signInEventChannel.trySend(SignInUiEvent.Failure("네트워크 문제가 발생하였습니다."))
                }
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("이메일과 비밀번호를 확인해주세요."))
            }
        }
    }
    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        object Success: SignInUiEvent()
    }
}