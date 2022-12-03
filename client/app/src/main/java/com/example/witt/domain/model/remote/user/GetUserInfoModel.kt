package com.example.witt.domain.model.remote.user

data class GetUserInfoModel (
    val status: Boolean,
    val reason: String,
    val user: UserInfoModel
        )

data class UserInfoModel(
    val userId: String,
    val accountType: Int,
    val username: String,
    val password: String,
    val phoneNum : String?,
    val nickname: String?,
    val profileImage: String?
)