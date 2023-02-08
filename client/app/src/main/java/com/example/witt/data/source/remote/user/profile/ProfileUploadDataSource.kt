package com.example.witt.data.source.remote.user.profile

import com.example.witt.data.model.remote.user.request.ProfileUploadRequest
import com.example.witt.data.model.remote.user.response.ProfileUploadResponse

interface ProfileUploadDataSource {

    suspend fun uploadProfile(profileUploadRequest: ProfileUploadRequest): Result<ProfileUploadResponse>
}
