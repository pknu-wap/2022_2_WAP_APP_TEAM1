package com.example.witt.data.source.remote.signin.token_signin

import com.example.witt.data.api.SignInService
import com.example.witt.data.model.remote.user.response.TokenResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenSignInDataSourceImpl @Inject constructor(
    private val userService: SignInService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : TokenSignInDataSource {
    override suspend fun tokenSignIn(): Result<TokenResponse> =
        withContext(coroutineDispatcher) {
            try {
                val response = userService.tokenSignIn()
                if (response.status) {
                    Result.success(response)
                } else {
                    Result.success(userService.tokenSignIn())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
}
