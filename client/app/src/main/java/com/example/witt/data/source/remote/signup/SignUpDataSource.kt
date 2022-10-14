package com.example.witt.data.source.remote.signup

import com.example.witt.data.model.auth.request.SignUpRequest
import com.example.witt.data.model.auth.response.SignUpResponse

interface SignUpDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest) : Result<SignUpResponse>
}