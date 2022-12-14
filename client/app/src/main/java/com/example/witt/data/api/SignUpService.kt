package com.example.witt.data.api

import com.example.witt.data.model.remote.signup.request.SignUpRequest
import com.example.witt.data.model.remote.signup.response.SignUpResponse
import com.example.witt.data.model.remote.user.response.DuplicateEmailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpService {

    @POST("/api/user/new")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

    @GET("api/user/new")
    suspend fun duplicateEmailCheck(
        @Query("Username") Username: String
    ): DuplicateEmailResponse
}
