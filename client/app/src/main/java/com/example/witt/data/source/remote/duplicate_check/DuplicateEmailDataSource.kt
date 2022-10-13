package com.example.witt.data.source.remote.duplicate_check

import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.model.user.response.DuplicateEmailResponse

interface DuplicateEmailDataSource{

    suspend fun duplicateEmailCheck(duplicateEmailRequest: DuplicateEmailRequest) : Result<DuplicateEmailResponse>

}