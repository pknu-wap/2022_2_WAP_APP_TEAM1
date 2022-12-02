package com.example.witt.data.source.remote.user

import com.example.witt.data.model.user.response.GetUserInfoResponse

interface GetUserInfoDataSource {
    suspend fun getUserInfo() : Result<GetUserInfoResponse>
}