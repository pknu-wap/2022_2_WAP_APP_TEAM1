package com.example.witt.data.mapper

import com.example.witt.data.model.remote.signin.response.SignInResponse
import com.example.witt.data.model.remote.signup.response.SignUpResponse
import com.example.witt.data.model.remote.signin.response.SocialSignInResponse
import com.example.witt.data.model.local.UserProfile
import com.example.witt.data.model.remote.plan.make_plan.response.MakePlanResponse
import com.example.witt.data.model.remote.user.response.ProfileUploadResponse
import com.example.witt.data.model.remote.user.response.DuplicateEmailResponse
import com.example.witt.data.model.remote.user.response.TokenResponse
import com.example.witt.domain.model.remote.signin.SignInModel
import com.example.witt.domain.model.remote.signup.SignUpModel
import com.example.witt.domain.model.remote.signin.SocialSignInModel
import com.example.witt.domain.model.remote.user.ProfileUploadModel
import com.example.witt.domain.model.remote.signup.DuplicateEmailModel
import com.example.witt.domain.model.remote.signin.TokenModel
import com.example.witt.domain.model.remote.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.model.remote.user.UserProfileModel

fun SignUpResponse.toSignUpModel() =  SignUpModel(
    status = status,
    reason = reason
)
fun SignInResponse.toSignInModel() = SignInModel(
    status = status,
    reason = reason,
    isProfileExists = isProfileExists
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

fun MakePlanResponse.toMakePlanResponseModel() = MakePlanResponseModel(
    status = status,
    reason = reason,
    TripId = TripId
)