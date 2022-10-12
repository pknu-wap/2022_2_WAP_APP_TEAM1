package com.example.witt.presentation.ui.signup

sealed class SignUpEvent {

    object Submit: SignUpEvent()

    object DuplicateEmail: SignUpEvent()

}