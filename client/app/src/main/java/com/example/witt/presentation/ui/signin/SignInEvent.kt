package com.example.witt.presentation.ui.signin

sealed class SignInEvent {

    object Submit : SignInEvent()
    data class KakaoSignIn(val oauthId: String) : SignInEvent()
    data class NaverSignIn(val oauthId: String) : SignInEvent()
    object CheckToken : SignInEvent()
}
