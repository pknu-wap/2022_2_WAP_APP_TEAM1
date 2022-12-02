package com.example.witt.data.model.user.response

import com.example.witt.domain.model.user.GetUserInfoModel
import com.example.witt.domain.model.user.UserInfoModel

data class GetUserInfoResponse (
    val status: Boolean,
    val reason: String,
    val user: UserInfoResponse
        )

data class UserInfoResponse(
    val UserId: String,
    val AccountType: Int,
    val Username: String,
    val Password: String,
    val PhoneNum : String?,
    val Nickname: String?,
    val ProfileImage: String?
)

fun GetUserInfoResponse.toGetUserInfoModel() = GetUserInfoModel(
    status = status,
    reason = reason,
    user = user.toUserInfoModel()
)

fun UserInfoResponse.toUserInfoModel() = UserInfoModel(
    userId = UserId,
    accountType = AccountType,
    username = Username,
    password = Password,
    phoneNum = PhoneNum,
    nickname = Nickname,
    profileImage = ProfileImage
)