package com.example.witt.data.source.remote.signup.duplicate_check

import com.example.witt.data.api.SignUpService
import com.example.witt.data.model.remote.user.request.DuplicateEmailRequest
import com.example.witt.data.model.remote.user.response.DuplicateEmailResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DuplicateEmailDataSourceImpl @Inject constructor(
    private val duplicateEmailService: SignUpService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : DuplicateEmailDataSource {
    override suspend fun duplicateEmailCheck(duplicateEmailRequest: DuplicateEmailRequest): Result<DuplicateEmailResponse> =
        withContext(coroutineDispatcher) {
            try {
                val response = duplicateEmailService.duplicateEmailCheck(
                    duplicateEmailRequest.Username
                )
                Result.success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
}
