package com.example.witt.data.api

import com.example.witt.data.model.auth.request.SignUpRequest
import com.example.witt.data.model.auth.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/users/register")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse
}