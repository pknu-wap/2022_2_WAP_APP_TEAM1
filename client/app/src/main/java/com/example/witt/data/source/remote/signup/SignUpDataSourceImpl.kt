package com.example.witt.data.source.remote.signup

import com.example.witt.data.api.SignUpService
import com.example.witt.data.model.auth.request.SignUpRequest
import com.example.witt.data.model.auth.response.SignUpResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SignUpDataSourceImpl @Inject constructor (
    private val signUpService: SignUpService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): SignUpDataSource{
    override suspend fun signUp(signUpRequest: SignUpRequest): Result<SignUpResponse> =
        withContext(coroutineDispatcher){
            try{
                val response = signUpService.signUp(
                    signUpRequest
                )
                Result.success(response)
            }catch(e: Exception){
                e.printStackTrace()
                Result.failure(e)
            }
    }
}