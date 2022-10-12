package com.example.witt.data.auth

import com.example.witt.data.auth.request.SignInRequest
import com.example.witt.data.auth.request.SignUpRequest
import com.example.witt.data.auth.response.SignInResponse
import com.example.witt.data.auth.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    @POST("/api/users/register")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

}