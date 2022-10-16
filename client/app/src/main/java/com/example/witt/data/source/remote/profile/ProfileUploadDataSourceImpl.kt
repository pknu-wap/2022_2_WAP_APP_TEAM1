package com.example.witt.data.source.remote.profile

import com.example.witt.data.api.ProfileUploadService
import com.example.witt.data.model.profile.request.ProfileUploadRequest
import com.example.witt.data.model.profile.response.ProfileUploadResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProfileUploadDataSourceImpl @Inject constructor(
    private val profileUploadService: ProfileUploadService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ProfileUploadDataSource{
    override suspend fun uploadProfile(profileUploadRequest: ProfileUploadRequest): Result<ProfileUploadResponse> =
        withContext(coroutineDispatcher){
             try{
                val response = profileUploadService
                    .uploadProfile(
                    profile = MultipartBody.Part
                        .createFormData(
                            "profile",
                            profileUploadRequest.profile.name,
                            profileUploadRequest.profile.asRequestBody()
                        ),
                    Nickname =  profileUploadRequest.Nickname.toRequestBody("text/plain".toMediaTypeOrNull()),
                    PhoneNum =  profileUploadRequest.PhoneNum.toRequestBody("text/plain".toMediaTypeOrNull())
                )
                Result.success(response)
            }catch(e: Exception){
                e.printStackTrace()
                Result.failure(e)
            }
    }
}