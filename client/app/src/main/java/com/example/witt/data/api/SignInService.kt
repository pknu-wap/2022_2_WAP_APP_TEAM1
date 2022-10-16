package com.example.witt.data.api

import com.example.witt.data.model.auth.request.SignInRequest
import com.example.witt.data.model.auth.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {

    @POST("/api/user/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

}