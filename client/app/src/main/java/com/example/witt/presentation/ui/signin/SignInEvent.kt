package com.example.witt.presentation.ui.signin

sealed class SignInEvent {

    object Submit: SignInEvent()

    object KakaoSignIn: SignInEvent()
    object NaverSignIn: SignInEvent()
    object CheckToken: SignInEvent()
}