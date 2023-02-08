package com.example.witt.data.source.remote.signin

import com.example.witt.data.api.SignInService
import com.example.witt.data.model.remote.signin.request.SignInRequest
import com.example.witt.data.model.remote.signin.response.SignInResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val service: SignInService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SignInDataSource {
    override suspend fun signIn(signInRequest: SignInRequest): Result<SignInResponse> =
        withContext(coroutineDispatcher) {
            try {
                val response = service.signIn(
                    SignInRequest(signInRequest.AccountType, signInRequest.Username, signInRequest.Password)
                )
                Result.success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
}
