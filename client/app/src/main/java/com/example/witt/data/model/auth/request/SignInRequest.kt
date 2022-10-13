package com.example.witt.data.model.auth.request

data class SignInRequest(
    val AccountType : Int,
    val Username : String,
    val Password: String
)
