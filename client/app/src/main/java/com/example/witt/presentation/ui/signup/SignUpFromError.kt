package com.example.witt.presentation.ui.signup

data class SignUpFromError (
    val emailError: String = "",
    val passwordError: String = "",
    val repeatedPasswordErrro: String = "",
    )