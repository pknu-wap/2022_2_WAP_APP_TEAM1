package com.example.witt.data.source.remote.signin

import com.example.witt.data.model.auth.request.SignInRequest
import com.example.witt.data.model.auth.response.SignInResponse

interface SignInDataSource {
    suspend fun signIn(signInRequest: SignInRequest) : Result<SignInResponse>
}