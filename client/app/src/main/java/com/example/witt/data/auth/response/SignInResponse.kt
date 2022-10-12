package com.example.witt.data.auth.response

import com.example.witt.domain.model.SignInModel

data class SignInResponse (
    val status : Boolean,
    val reason: String,
    val AccessToken : String,
    val RefreshToken : String
        )

fun SignInResponse.toSignInModel() = SignInModel(
        status = status,
        reason = reason
)
