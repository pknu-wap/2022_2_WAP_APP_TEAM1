package com.example.witt.data.auth.response

import com.example.witt.domain.model.SignUpModel

data class SignUpResponse (
    val status: Boolean,
    val reason: String
        )

fun SignUpResponse.toSignUpModel() =  SignUpModel(
        status = status,
        reason = reason
)