package com.example.witt.data.api

import com.example.witt.data.model.user.response.UserTokenResponse
import retrofit2.http.GET

interface UserTokenService {

    @GET("/api/user/me")
    suspend fun tokenSignIn(
    ): UserTokenResponse

}