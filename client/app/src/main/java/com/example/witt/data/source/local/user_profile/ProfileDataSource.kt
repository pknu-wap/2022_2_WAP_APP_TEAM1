package com.example.witt.data.source.local.user_profile

import com.example.witt.data.model.local.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileDataSource {

    fun getProfile(): Flow<Result<UserProfile>>

    suspend fun setProfile(userProfile: UserProfile) : Result<Unit>
}