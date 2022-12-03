package com.example.witt.data.api

import com.example.witt.data.model.remote.user.response.ProfileUploadResponse
import com.example.witt.data.model.remote.user.response.GetUserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {

    @Multipart
    @PATCH("/api/user/me")
    suspend fun uploadProfile(
        @Part profile: MultipartBody.Part,
        @Part("Nickname") Nickname : RequestBody,
        @Part("PhoneNum") PhoneNum : RequestBody
    ): ProfileUploadResponse


    @GET("/api/user/me")
    suspend fun getUserInfo(): GetUserInfoResponse

}