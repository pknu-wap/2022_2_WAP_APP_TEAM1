package com.example.witt.data.source.remote.social_signin

import com.example.witt.data.api.SignInService
import com.example.witt.data.model.auth.request.SocialSignInRequest
import com.example.witt.data.model.auth.response.SocialSignInResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SocialSignInDataSourceImpl @Inject constructor(
    private val socialSignInService: SignInService,
     @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SocialSignInDataSource{
    override suspend fun signIn(socialSignInRequest: SocialSignInRequest): Result<SocialSignInResponse> =
        withContext(coroutineDispatcher){
            runCatching {
                val response = socialSignInService.signIn(socialSignInRequest)
                response
            }
        }.onFailure { exception ->
            exception.printStackTrace()
        }
}