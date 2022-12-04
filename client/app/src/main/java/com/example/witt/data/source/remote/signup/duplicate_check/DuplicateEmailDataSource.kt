package com.example.witt.data.source.remote.signup.duplicate_check

import com.example.witt.data.model.remote.user.request.DuplicateEmailRequest
import com.example.witt.data.model.remote.user.response.DuplicateEmailResponse

interface DuplicateEmailDataSource {

    suspend fun duplicateEmailCheck(duplicateEmailRequest: DuplicateEmailRequest): Result<DuplicateEmailResponse>
}
