package com.example.witt.data.mapper

import com.example.witt.data.api.auth.response.SignInResponse
import com.example.witt.data.api.auth.response.SignUpResponse
import com.example.witt.data.api.user.response.DuplicateEmailResponse
import com.example.witt.domain.model.SignInModel
import com.example.witt.domain.model.SignUpModel
import com.example.witt.domain.model.user.DuplicateEmailModel

fun SignUpResponse.toSignUpModel() =  SignUpModel(
    status = status,
    reason = reason
)
fun SignInResponse.toSignInModel() = SignInModel(
    status = status,
    reason = reason
)
fun DuplicateEmailResponse.toDuplicateEmailModel() =
    DuplicateEmailModel(
        status = status,
        reason = reason
    )