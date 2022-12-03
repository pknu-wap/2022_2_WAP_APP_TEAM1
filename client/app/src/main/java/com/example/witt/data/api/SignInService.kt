package com.example.witt.data.api

import com.example.witt.data.model.auth.request.SignInRequest
import com.example.witt.data.model.auth.request.SocialSignInRequest
import com.example.witt.data.model.auth.response.SignInResponse
import com.example.witt.data.model.auth.response.SocialSignInResponse
import com.example.witt.data.model.user.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInService {

    @POST("/api/user/me")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    @GET("/api/user/me")
    suspend fun tokenSignIn(
    ): TokenResponse

    @POST("/api/user/me")
    suspend fun signIn(
        @Body request: SocialSignInRequest
    ): SocialSignInResponse

}