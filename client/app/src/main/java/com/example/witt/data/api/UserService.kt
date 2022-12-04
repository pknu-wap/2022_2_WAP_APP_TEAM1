package com.example.witt.data.api

import com.example.witt.data.model.remote.user.response.GetUserInfoResponse
import com.example.witt.data.model.remote.user.response.ProfileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserService {

    @Multipart
    @PATCH("/api/user/me")
    suspend fun uploadProfile(
        @Part profile: MultipartBody.Part,
        @Part("Nickname") Nickname: RequestBody,
        @Part("PhoneNum") PhoneNum: RequestBody
    ): ProfileUploadResponse

    @GET("/api/user/me")
    suspend fun getUserInfo(): GetUserInfoResponse
}
