package com.example.witt.data.api

import com.example.witt.data.model.user.response.TokenResponse
import retrofit2.http.GET

interface TokenSignInService {

    @GET("/api/user/me")
    suspend fun tokenSignIn(
    ): TokenResponse

}