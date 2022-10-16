package com.example.witt.data.model.user.response

data class UserTokenResponse (
    val status: Boolean,
    val reason: String,
    val result : UserInfo
        )

data class UserInfo(
    val  USER_ID : String,
    val  ACCOUNTTYPE : Int,
    val  OAUTH_ID: String?,
    val  USERNAME: String,
    val  PASSWORD: String,
    val  PHONENUM : String?,
    val  NICKNAME: String?,
    val  PROFILEIMAGE: String?
)