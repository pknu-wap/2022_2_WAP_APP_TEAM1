package com.example.witt.data.api

import com.example.witt.data.model.profile.response.ProfileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProfileUploadService {

    @Multipart
    @POST("/api/user/me")
    suspend fun uploadProfile(
        @Part profile: MultipartBody.Part,
        @Part("Nickname") Nickname : RequestBody,
        @Part("PhoneNum") PhoneNum : RequestBody
    ): ProfileUploadResponse

}