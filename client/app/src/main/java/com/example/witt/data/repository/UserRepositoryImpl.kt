package com.example.witt.data.repository

import android.content.SharedPreferences
import com.example.witt.data.model.user.request.DuplicateEmailRequest
import com.example.witt.data.mapper.toDuplicateEmailModel
import com.example.witt.data.mapper.toUserProfileModel
import com.example.witt.data.mapper.toUserTokenModel
import com.example.witt.data.model.local.UserProfile
import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.user.UserTokenDataSource
import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.model.user.UserProfileModel
import com.example.witt.domain.model.user.UserTokenModel
import com.example.witt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val duplicateEmailDataSource: DuplicateEmailDataSource,
    private val userTokenDataSource: UserTokenDataSource,
    private val profileDataSource: ProfileDataSource
):UserRepository {
    override suspend fun duplicateEmail(email: String): Result<DuplicateEmailModel> {
        return duplicateEmailDataSource.duplicateEmailCheck(DuplicateEmailRequest(email))
            .mapCatching { response ->
                response.toDuplicateEmailModel()
            }
    }

    override suspend fun userTokenSignIn(accessToken: String, refreshToken: String): Result<UserTokenModel> {
        return userTokenDataSource.userTokenSignIn(accessToken, refreshToken)
            .mapCatching { response ->
                response.toUserTokenModel()
            }
    }

    override suspend fun setProfile(profileUri: String, userName: String): Result<Unit> {
        return profileDataSource.setProfile(UserProfile(userName, profileUri))
    }

    override fun getProfile(): Flow<Result<UserProfileModel>> {
        return profileDataSource.getProfile().map { result ->
            result.mapCatching { userProfile ->
                userProfile.toUserProfileModel()
            }
        }
    }
}