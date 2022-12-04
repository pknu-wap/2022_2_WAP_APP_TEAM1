package com.example.witt.data.api

import com.example.witt.data.model.remote.signin.request.SignInRequest
import com.example.witt.data.model.remote.signin.request.SocialSignInRequest
import com.example.witt.data.model.remote.signin.response.SignInResponse
import com.example.witt.data.model.remote.signin.response.SocialSignInResponse
import com.example.witt.data.model.remote.user.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInService {

    @POST("/api/user/me")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    @GET("/api/user/me")
    suspend fun tokenSignIn(): TokenResponse

    @POST("/api/user/me")
    suspend fun signIn(
        @Body request: SocialSignInRequest
    ): SocialSignInResponse
}
