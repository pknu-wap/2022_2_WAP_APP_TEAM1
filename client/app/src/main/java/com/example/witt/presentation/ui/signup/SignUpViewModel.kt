package com.example.witt.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.witt.domain.use_case.validate.ValidateEmail
import com.example.witt.domain.use_case.validate.ValidatePassword
import com.example.witt.domain.use_case.validate.ValidateRepeatedPassword
import com.example.witt.presentation.ui.signin.SignInViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SignUpViewModel: ViewModel(){

    private val validateEmail by lazy { ValidateEmail() }
    private val validatePassword by lazy { ValidatePassword() }
    private val validateRepeatedPassword by lazy { ValidateRepeatedPassword() }

    private val signUpEventChannel = Channel<SignUpUiEvent>()
    val signUpEvents = signUpEventChannel.receiveAsFlow()

    //dataBinding 으로 값 가져오기, 더 좋은 방법이 없을까?
    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()
    val inputRepeatedPassword : MutableLiveData<String> = MutableLiveData()

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
        signUpEventChannel.trySend(SignUpUiEvent.Success)
    }

    sealed class SignUpUiEvent{
        data class Failure(val message: String?): SignUpUiEvent()
        object Success: SignUpUiEvent()
    }
}