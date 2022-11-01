package com.example.witt.data.api

import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface DuplicateEmailService {

    @GET("/api/user/duplicate")
    suspend fun duplicateEmailCheck(
        @Header("Username") request: DuplicateEmailRequest
    ): DuplicateEmailResponse

}