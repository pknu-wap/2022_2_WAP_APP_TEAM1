package com.example.witt.data.api

import com.example.witt.data.model.user.response.UserTokenResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserTokenService {

    @GET("/api/users/me")
    suspend fun tokenSignIn(
        @Header("Authorization") useToken: String
    ): UserTokenResponse

}