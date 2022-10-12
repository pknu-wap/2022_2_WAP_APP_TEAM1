package com.example.witt.domain.repository

import com.example.witt.domain.model.user.DuplicateEmailModel

interface UserRepository {

    suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel>
}