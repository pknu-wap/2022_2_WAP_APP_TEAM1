package com.example.witt.presentation.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.witt.domain.use_case.validate.ValidateEmail
import com.example.witt.domain.use_case.validate.ValidatePassword
import com.example.witt.domain.use_case.validate.ValidateRepeatedPassword

class SignUpViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword()
): ViewModel(){

    //dataBinding 으로 값 가져오기, 더 좋은 방법이 없을까?
    val inputEmail = MutableLiveData("")
    val inputPassword = MutableLiveData("")
    val inputRepeatedPassword = MutableLiveData("")

    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.SubmitEmailPassword ->{

            }
            is SignUpEvent.SubmitNamePhoneNumber ->{

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
            return
        }
        submitData()
    }

    private fun submitData(){
        //kakao access token 가져오기 및 데이터 전송
    }

}