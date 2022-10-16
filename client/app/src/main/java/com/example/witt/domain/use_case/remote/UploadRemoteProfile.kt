package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.profile.remote.ProfileUploadModel
import com.example.witt.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UploadRemoteProfile @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(profile: File, Nickname: String, PhoneNum: String): Result<ProfileUploadModel> =
        userRepository.uploadProfile(profile, Nickname, PhoneNum)
}