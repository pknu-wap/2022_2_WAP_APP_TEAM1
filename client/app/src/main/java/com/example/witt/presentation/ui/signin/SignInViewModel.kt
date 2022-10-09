package com.example.witt.presentation.ui.signin

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

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()

    private val validateEmail by lazy{ ValidateEmail() }
    private val validatePassword by lazy { ValidatePassword()}

    val inputEmail: MutableLiveData<String> = MutableLiveData("")
    val inputPassword: MutableLiveData<String> = MutableLiveData("")


    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.SubmitEmailPassword ->{
                submitData()
            }
            is SignUpEvent.SubmitNamePhoneNumber ->{

            }
        }
    }

    private fun validateData(){
        val emailResult = validateEmail.execute(inputEmail.value ?: "")
        val passwordResult = validatePassword.execute(inputPassword.value ?: "")

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any{
            !it.successful
        }
        if(hasError){
            return
        }
        submitData()
    }

    private fun submitData(){
        //kakao access token 가져오기 및 데이터 전송
        viewModelScope.launch {
            val signInResult =
                signInEmailPassword(accountType = 0, inputEmail.value ?: "", inputPassword.value ?: "")
            if(signInResult.isSuccess){
                signInEventChannel.trySend(SignInUiEvent.Success)
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("error"))
            }
        }
    }
    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        object Success: SignInUiEvent()
    }
}