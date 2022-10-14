package com.example.witt.data.mapper

import com.example.witt.data.model.auth.response.SignInResponse
import com.example.witt.data.model.auth.response.SignUpResponse
import com.example.witt.data.model.local.UserProfile
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import com.example.witt.data.model.user.response.UserTokenResponse
import com.example.witt.domain.model.auth.SignInModel
import com.example.witt.domain.model.auth.SignUpModel
import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.model.user.UserProfileModel
import com.example.witt.domain.model.user.UserTokenModel

fun SignUpResponse.toSignUpModel() =  SignUpModel(
    status = status,
    reason = reason
)
fun SignInResponse.toSignInModel() = SignInModel(
    status = status,
    reason = reason
)
fun DuplicateEmailResponse.toDuplicateEmailModel() = DuplicateEmailModel(
        status = status,
        reason = reason
)

fun UserTokenResponse.toUserTokenModel()= UserTokenModel(
    status = status,
    reason = reason
)
fun UserProfile.toUserProfileModel() = UserProfileModel(
    userName = userName,
    profileUri = profileUri
)