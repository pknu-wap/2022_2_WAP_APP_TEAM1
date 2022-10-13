package com.example.witt.data.repository

import com.example.witt.data.api.DefaultApiService
import com.example.witt.data.api.user.request.DuplicateEmailRequest
import com.example.witt.data.mapper.toDuplicateEmailModel
import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DefaultApiService
):UserRepository{
    override suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel> {
        return try{
            val response = api.duplicateEmailCheck(DuplicateEmailRequest(email))
            Result.success(response.toDuplicateEmailModel())
        } catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}