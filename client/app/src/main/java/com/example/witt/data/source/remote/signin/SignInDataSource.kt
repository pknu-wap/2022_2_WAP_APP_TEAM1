package com.example.witt.data.source.remote.signin

import com.example.witt.data.model.remote.signin.request.SignInRequest
import com.example.witt.data.model.remote.signin.response.SignInResponse

interface SignInDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Result<SignInResponse>
}
