package com.example.witt.data.source.remote.profile

import com.example.witt.data.model.profile.request.ProfileUploadRequest
import com.example.witt.data.model.profile.response.ProfileUploadResponse
import java.io.File

interface ProfileUploadDataSource {

    suspend fun uploadProfile(profileUploadRequest: ProfileUploadRequest) : Result<ProfileUploadResponse>

}