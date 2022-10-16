package com.example.witt.data.source.remote.user

import com.example.witt.data.model.user.response.UserTokenResponse

interface UserTokenDataSource {

    suspend fun userTokenSignIn(accessToken: String, refreshToken: String) : Result<UserTokenResponse>
}