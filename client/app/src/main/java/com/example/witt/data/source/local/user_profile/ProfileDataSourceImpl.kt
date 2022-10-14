package com.example.witt.data.source.local.user_profile

import com.example.witt.data.database.ProfileDao
import com.example.witt.data.model.local.UserProfile
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileDao: ProfileDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): ProfileDataSource{

    override fun getProfile(): Flow<Result<UserProfile>> =
        profileDao.getProfile().map{ profile ->
            Result.success(profile)
        }.catch { e ->
            emit(Result.failure(e))
        }.flowOn(coroutineDispatcher)

    override suspend fun setProfile(userProfile: UserProfile) : Result<Unit> =
        withContext(coroutineDispatcher){
         try {
            Result.success(profileDao.setProfile(userProfile))
        }catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}