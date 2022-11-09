package com.example.witt.data.api

import com.example.witt.data.model.auth.request.SocialSignInRequest
import com.example.witt.data.model.auth.response.SocialSignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SocialSignInService {

    @POST("/api/user/me")
    suspend fun signIn(
        @Body request: SocialSignInRequest
    ): SocialSignInResponse

}