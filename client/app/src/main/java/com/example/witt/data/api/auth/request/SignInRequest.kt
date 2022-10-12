package com.example.witt.data.api.auth.request

data class SignInRequest(
    val AccountType : Int,
    val Username : String,
    val Password: String
)
