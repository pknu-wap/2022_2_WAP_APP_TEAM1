package com.example.witt.data.api

import com.example.witt.data.model.user.response.DuplicateEmailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DuplicateEmailService {

    @GET("api/user/new")
    suspend fun duplicateEmailCheck(
        @Query("Username") Username: String
    ): DuplicateEmailResponse

}