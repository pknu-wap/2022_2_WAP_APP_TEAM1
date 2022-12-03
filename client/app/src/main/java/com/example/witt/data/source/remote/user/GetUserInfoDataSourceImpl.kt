package com.example.witt.data.source.remote.user

import com.example.witt.data.api.UserService
import com.example.witt.data.model.remote.user.response.GetUserInfoResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoDataSourceImpl @Inject constructor(
    private val service: UserService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): GetUserInfoDataSource{
    override suspend fun getUserInfo(): Result<GetUserInfoResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.getUserInfo()
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}