package com.example.witt.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.use_case.remote.DuplicateEmail
import com.example.witt.domain.use_case.remote.SignUpEmailPassword
import com.example.witt.domain.use_case.validate.ValidateEmail
import com.example.witt.domain.use_case.validate.ValidatePassword
import com.example.witt.domain.use_case.validate.ValidateRepeatedPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUpEmailPassword,
    private val duplicateEmail: DuplicateEmail
): ViewModel(){

    private val validateEmail by lazy { ValidateEmail() }
    private val validatePassword by lazy { ValidatePassword() }
    private val validateRepeatedPassword by lazy { ValidateRepeatedPassword() }

    private val signUpEventChannel = Channel<SignUpUiEvent>()
    val signUpEvents = signUpEventChannel.receiveAsFlow()

    //dataBinding 으로 값 가져오기, 더 좋은 방법이 없을까?
    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()
    val inputRepeatedPassword : MutableLiveData<String> = MutableLiveData()
    //이메일 중복 체크
    private val duplicateCheckedState : MutableLiveData<Boolean> = MutableLiveData()

    private val _errorEmail : MutableLiveData<String> = MutableLiveData()
    val errorEmail : LiveData<String> get() = _errorEmail

    private val _errorPassword : MutableLiveData<String> = MutableLiveData()
    val errorPassword: LiveData<String> get() = _errorPassword

    private val _errorRepeatedPassword : MutableLiveData<String> = MutableLiveData()
    val errorRepeatedPassword: LiveData<String> get() = _errorRepeatedPassword


    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.Submit ->{
                validateData()
            }
            is SignUpEvent.DuplicateEmail ->{
                duplicateCheckData()
            }
        }
    }

    private fun validateData(){
        val emailResult = validateEmail.execute(inputEmail.value ?: "")
        val passwordResult = validatePassword.execute(inputPassword.value ?: "")
        val repeatedPasswordResult = validateRepeatedPassword.execute(inputPassword.value ?: "", inputRepeatedPassword.value ?: "")

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any{
            !it.successful
        }
        if(hasError){
            emailResult.errorMessage?.let { errorMessage ->
                _errorEmail.value = errorMessage
            }
            passwordResult.errorMessage?.let{ errorMessage ->
                _errorPassword.value = errorMessage
            }
            repeatedPasswordResult.errorMessage?.let{ errorMessage ->
                _errorRepeatedPassword.value = errorMessage
            }
            return
        }
        submitData()
    }

    private fun submitData(){
        if(duplicateCheckedState.value == true) {
            viewModelScope.launch {
                val result = signUp(
                    email = inputEmail.value ?: "",
                    password = inputPassword.value ?: ""
                )
                result.mapCatching {
                    if (it.status) {
                        signUpEventChannel.trySend(SignUpUiEvent.Success)
                    } else {
                        signUpEventChannel.trySend(SignUpUiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    signUpEventChannel.trySend(SignUpUiEvent.Failure("네트워크 문제가 발생하였습니다."))
                }
            }
        }else{
            signUpEventChannel.trySend(SignUpUiEvent.Failure("이메일 중복을 확인해주세요."))
        }
    }

    private fun duplicateCheckData(){
        viewModelScope.launch {
            val result = duplicateEmail(
                email = inputEmail.value ?: ""
            )
            result.mapCatching {
                if(it.status){
                    signUpEventChannel.trySend(SignUpUiEvent.DuplicateChecked(it.reason))
                    duplicateCheckedState.value = true
                }else{
                    signUpEventChannel.trySend(SignUpUiEvent.Failure(it.reason))
                }
            }.onFailure {
                signUpEventChannel.trySend(SignUpUiEvent.Failure("네트워크 문제가 발생하였습니다."))
            }
        }
    }

    //이메일 변경시 다시 체크해야됨
    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int){
        duplicateCheckedState.value = false
    }

    sealed class SignUpUiEvent{
        data class Failure(val message: String?): SignUpUiEvent()
        data class DuplicateChecked(val message: String?): SignUpUiEvent()
        object Success: SignUpUiEvent()
    }
}