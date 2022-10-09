package com.example.witt.data.auth

import com.example.witt.data.auth.request.SignInRequest
import com.example.witt.data.auth.response.SignInResponse
import retrofit2.http.Body

import retrofit2.http.POST

interface AuthApi {

    @POST("/api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse
}