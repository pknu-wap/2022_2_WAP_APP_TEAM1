package com.example.witt.data.api.auth.response

data class SignInResponse (
    val status : Boolean,
    val reason: String,
    val AccessToken : String,
    val RefreshToken : String
        )
