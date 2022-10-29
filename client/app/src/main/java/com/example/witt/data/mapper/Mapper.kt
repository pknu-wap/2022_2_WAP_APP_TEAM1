package com.example.witt.data.mapper

import com.example.witt.data.model.auth.response.SignInResponse
import com.example.witt.data.model.auth.response.SignUpResponse
import com.example.witt.data.model.auth.response.SocialSignInResponse
import com.example.witt.data.model.local.UserProfile
import com.example.witt.data.model.profile.response.ProfileUploadResponse
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import com.example.witt.data.model.user.response.TokenResponse
import com.example.witt.domain.model.auth.SignInModel
import com.example.witt.domain.model.auth.SignUpModel
import com.example.witt.domain.model.auth.SocialSignInModel
import com.example.witt.domain.model.profile.remote.ProfileUploadModel
import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.model.auth.TokenModel
import com.example.witt.domain.model.user.UserProfileModel

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

fun TokenResponse.toTokenModel()= TokenModel(
    status = status,
    reason = reason
)
fun UserProfile.toUserProfileModel() = UserProfileModel(
    userName = userName,
    profileUri = profileUri
)
fun ProfileUploadResponse.toProfileUploadModel() = ProfileUploadModel(
    status = status,
    reason = reason
)
fun SocialSignInResponse.toSocialSignInModel() = SocialSignInModel(
    status = status,
    reason = reason,
    isProfileExists = isProfileExists
)