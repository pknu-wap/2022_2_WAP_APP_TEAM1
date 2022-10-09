package com.example.witt.data.auth.response

data class SignInResponse (
    val status : Boolean,
    val reason: String,
    val AccessToken : String,
    val RefreshToken : String
        )