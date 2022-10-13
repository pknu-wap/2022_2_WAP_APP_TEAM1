package com.example.witt.data.repository

import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.mapper.toDuplicateEmailModel
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: DuplicateEmailDataSource
):UserRepository{
    override suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel> {
        return dataSource.duplicateEmailCheck(DuplicateEmailRequest(email)).mapCatching { response ->
            response.toDuplicateEmailModel()
        }
    }
}