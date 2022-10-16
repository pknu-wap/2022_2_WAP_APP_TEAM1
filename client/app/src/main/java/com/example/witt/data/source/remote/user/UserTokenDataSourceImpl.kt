package com.example.witt.data.source.remote.user

import com.example.witt.data.api.UserTokenService
import com.example.witt.data.model.user.response.UserTokenResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserTokenDataSourceImpl @Inject constructor(
    private val userService: UserTokenService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UserTokenDataSource{
    override suspend fun userTokenSignIn(): Result<UserTokenResponse> =
        withContext(coroutineDispatcher){
            try{
                val response = userService.tokenSignIn()
                if(response.status){
                    Result.success(response)
                }else{
                    Result.success(userService.tokenSignIn())
                }
            }catch(e: Exception){
                e.printStackTrace()
                Result.failure(e)
            }
        }
}