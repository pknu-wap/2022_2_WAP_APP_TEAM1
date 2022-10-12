package com.example.witt.data.api

import com.example.witt.data.api.auth.request.SignInRequest
import com.example.witt.data.api.auth.request.SignUpRequest
import com.example.witt.data.api.auth.response.SignInResponse
import com.example.witt.data.api.auth.response.SignUpResponse
import com.example.witt.data.api.user.request.DuplicateEmailRequest
import com.example.witt.data.api.user.response.DuplicateEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DefaultApiService {

    @POST("/api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    @POST("/api/users/register")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

    @POST("/api/users/duplicate")
    suspend fun duplicateEmailCheck(
        @Body request: DuplicateEmailRequest
    ): DuplicateEmailResponse

}