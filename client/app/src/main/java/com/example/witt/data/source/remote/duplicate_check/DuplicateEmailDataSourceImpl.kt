package com.example.witt.data.source.remote.duplicate_check

import com.example.witt.data.api.DuplicateEmailService
import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.model.user.response.DuplicateEmailResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DuplicateEmailDataSourceImpl @Inject constructor(
    private val duplicateEmailService: DuplicateEmailService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
):DuplicateEmailDataSource{
    override suspend fun duplicateEmailCheck(duplicateEmailRequest: DuplicateEmailRequest): Result<DuplicateEmailResponse>
    = withContext(coroutineDispatcher) {
        try {
            val response = duplicateEmailService.duplicateEmailCheck(
                request = duplicateEmailRequest
            )
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}