package com.example.witt.domain.repository

import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.model.user.UserProfileModel
import com.example.witt.domain.model.user.UserTokenModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel>

    suspend fun userTokenSignIn(accessToken: String, refreshToken: String): Result<UserTokenModel>

    suspend fun setProfile(profileUri: String, userName: String): Result<Unit>

    fun getProfile(): Flow<Result<UserProfileModel>>
}