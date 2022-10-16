package com.example.witt.data.api

import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DuplicateEmailService {

    @POST("/api/user/duplicate")
    suspend fun duplicateEmailCheck(
        @Body request: DuplicateEmailRequest
    ): DuplicateEmailResponse

}