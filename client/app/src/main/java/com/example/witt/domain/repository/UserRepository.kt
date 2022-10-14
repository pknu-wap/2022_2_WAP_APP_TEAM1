package com.example.witt.domain.repository

import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.model.user.UserTokenModel

interface UserRepository {

    suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel>

    suspend fun userTokenSignIn(accessToken: String, refreshToken: String): Result<UserTokenModel>

}