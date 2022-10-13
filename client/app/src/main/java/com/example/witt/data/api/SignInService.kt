package com.example.witt.data.api

import com.example.witt.data.model.auth.request.SignInRequest
import com.example.witt.data.model.auth.request.SignUpRequest
import com.example.witt.data.model.auth.response.SignInResponse
import com.example.witt.data.model.auth.response.SignUpResponse
import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {

    @POST("/api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

}