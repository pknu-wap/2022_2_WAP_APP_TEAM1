package com.example.witt.data.source.remote.signup

import com.example.witt.data.model.remote.signup.request.SignUpRequest
import com.example.witt.data.model.remote.signup.response.SignUpResponse

interface SignUpDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest): Result<SignUpResponse>
}
