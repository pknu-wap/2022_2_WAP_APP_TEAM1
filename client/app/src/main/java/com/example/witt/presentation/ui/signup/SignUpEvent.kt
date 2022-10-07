package com.example.witt.presentation.ui.signup

sealed class SignUpEvent {

    object SubmitEmailPassword: SignUpEvent()
    object SubmitNamePhoneNumber: SignUpEvent()
}